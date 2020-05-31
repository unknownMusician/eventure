package com.eventure.dao;

import java.util.Collection;

public interface AbstractDao<T> {
    T get(Integer id);

    Collection<T> findAll();

    void insert(T entity, boolean generateId);

    void delete(T entity);

    void update(T entity);
}
