package by.pantosha.itirod.lab4.repository;

import by.pantosha.itirod.lab4.Broker;
import by.pantosha.itirod.lab4.enities.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

public final class CheckingRepository</*Must be immutable.*/TEntity extends Entity> {
    private final Logger LOGGER = LogManager.getLogger(CheckingRepository.class);

    private final Broker broker;
    private final ConcurrentMap<UUID, TEntity> accounts;

    public CheckingRepository(Map<UUID, TEntity> accounts) {
        this.broker = new Broker();
        this.accounts = new ConcurrentHashMap<>(accounts);
    }

    public void beginTransaction(UUID... ids) {
        broker.beginTransaction(ids);
    }

    public void endTransaction(UUID... ids) {
        broker.endTransaction(ids);
    }

    public TEntity read(UUID id) {
        return accounts.get(id);
    }

    @SafeVarargs
    public final void update(TEntity... entities) {
        for (TEntity entity : entities) {
            accounts.replace(entity.getId(), entity);
        }
    }

    public void forEach(Consumer<? super TEntity> action) {
        broker.startChecking();
        for (UUID id : accounts.keySet()) {
            broker.beginCheck(id);
            action.accept(accounts.get(id));
            broker.endCheck(id);
        }
        broker.stopChecking();
    }
}