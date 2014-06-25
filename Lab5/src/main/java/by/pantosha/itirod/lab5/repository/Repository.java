package by.pantosha.itirod.lab5.repository;

import java.util.Collection;

public interface Repository<TKey, TEntity> {
    void create(TEntity entity);

    TEntity read(TKey key);

    Collection<TEntity> readAll();

    void update(TEntity entity);

    void delete(TKey key);
}