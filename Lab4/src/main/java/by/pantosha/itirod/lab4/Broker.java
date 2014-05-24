package by.pantosha.itirod.lab4;

import by.pantosha.itirod.lab4.utils.CheckingWorkUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class Broker {
    private final static Logger LOGGER = LogManager.getLogger(Broker.class);

    private final ReadWriteLock newCycleLock;
    private final ReentrantLock startChecking;
    private final ConcurrentMap<UUID, CheckingWorkUnit> checkingLocks;
    private final ConcurrentMap<UUID, ReentrantLock> transactionLocks;

    public Broker() {
        newCycleLock = new ReentrantReadWriteLock();
        startChecking = new ReentrantLock();
        checkingLocks = new ConcurrentHashMap<>();
        transactionLocks = new ConcurrentHashMap<>();
    }

    public void beginTransaction(UUID... ids) {
        LOGGER.debug("Transaction begin. {} ids in.", ids.length);
        Arrays.sort(ids);

        lockForTransaction(ids);

        newCycleLock.readLock().lock();
        lockForCheck(ids);

        List<UUID> notChecked = getNotChecked(ids);
        LOGGER.trace("Not checked in transaction: {}", notChecked.size());
        if (!notChecked.isEmpty() && !(notChecked.size() == ids.length)) {
            for (UUID id : notChecked) {
                CheckingWorkUnit workUnit = checkingLocks.get(id);
                Condition hasEnd = workUnit.getCheckEndCondition();
                try {
                    while (!workUnit.isChecked()) {
                        // FIX: effective only for transaction with two ids.
                        LOGGER.trace("Wait for signal check end ID: {}", id);
                        hasEnd.await(15, TimeUnit.MILLISECONDS);
                    }
                } catch (InterruptedException ex) {
                    LOGGER.warn("Interrupted: {}", ex.getMessage());
                }
            }
        }
        newCycleLock.readLock().unlock();
    }

    public void endTransaction(UUID... ids) {
        for (UUID id : ids) {
            unlockForCheck(id);
            unlockForTransaction(id);
        }
        LOGGER.debug("Transaction end. {} ids in.", ids.length);
    }

    public void startChecking() {
        startChecking.lock();
        newCycleLock.writeLock().lock();
        for (CheckingWorkUnit workUnit : checkingLocks.values()) {
            workUnit.reset();
        }
        newCycleLock.writeLock().unlock();
    }

    public void stopChecking() {
        newCycleLock.writeLock().lock();
        for (CheckingWorkUnit workUnit : checkingLocks.values()) {
            workUnit.reset();
        }
        newCycleLock.writeLock().unlock();
        startChecking.unlock();
    }

    public void beginCheck(UUID id) {
        lockForCheck(id);
    }

    public void endCheck(UUID id) {
        CheckingWorkUnit workUnit = checkingLocks.get(id);
        if (workUnit != null) {
            workUnit.check();
            workUnit.getCheckEndCondition().signal();
        }
        unlockForCheck(id);
    }

    private void lockForCheck(UUID... ids) {
        for (UUID id : ids) {
            LOGGER.trace("Try to lock for check {}.", id);
            CheckingWorkUnit workUnit = new CheckingWorkUnit(new ReentrantLock());
            CheckingWorkUnit previousUnit = checkingLocks.putIfAbsent(id, workUnit);
            if (previousUnit != null)
                workUnit = previousUnit;
            workUnit.getWorkUnit().lock();
            LOGGER.debug("Lock for check {}.", id);
        }
    }

    private void unlockForCheck(UUID... ids) {
        for (UUID id : ids) {
            CheckingWorkUnit workUnit = checkingLocks.get(id);
            if (workUnit != null) {
                workUnit.getWorkUnit().unlock();
                LOGGER.trace("Unlock for check ID: {}", id);
            } else {
                LOGGER.warn("Unlock for check. ID: {} do not exists.", id);
            }
        }
    }

    private void lockForTransaction(UUID... ids) {
        for (UUID id : ids) {
            LOGGER.trace("Try to lock for transaction. ID: {}.", id);
            ReentrantLock lock = new ReentrantLock();
            ReentrantLock previousLock = transactionLocks.putIfAbsent(id, lock);
            if (previousLock != null)
                lock = previousLock;
            else {
                LOGGER.trace("Insert new transaction lock");
            }
            lock.lock();
            LOGGER.debug("Lock for transaction. ID: {}.", id);
        }
    }

    private void unlockForTransaction(UUID... ids) {
        for (UUID id : ids) {
            ReentrantLock lock = transactionLocks.get(id);
            if (lock != null) {
                lock.unlock();
                LOGGER.trace("Unlock for transaction ID: {}", id);
            } else {
                LOGGER.error("Unlock for transaction. ID: {} do not exists.", id);
            }
        }
    }

    private List<UUID> getNotChecked(UUID... ids) {
        List<UUID> notChecked = new ArrayList<>();
        for (UUID id : ids) {
            if (!isChecked(id)) {
                notChecked.add(id);
            }
        }
        return notChecked;
    }

    private boolean isChecked(UUID id) {
        CheckingWorkUnit workUnit = checkingLocks.get(id);
        return (workUnit != null) && workUnit.isChecked();
    }
}