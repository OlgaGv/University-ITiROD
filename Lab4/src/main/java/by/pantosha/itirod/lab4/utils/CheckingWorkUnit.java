package by.pantosha.itirod.lab4.utils;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public final class CheckingWorkUnit extends WorkUnit<Lock> {
    private final Condition hasCheckEnd;

    private AtomicBoolean hasChecked;

    public CheckingWorkUnit(Lock lock) {
        super(lock);
        hasCheckEnd = workUnit.newCondition();
        hasChecked = new AtomicBoolean(false);
    }

    public void check() {
        hasChecked.set(true);
    }

    public void reset() {
        hasChecked.set(false);
    }

    public boolean isChecked() {
        return hasChecked.get();
    }

    public Condition getCheckEndCondition() {
        return hasCheckEnd;
    }
}
