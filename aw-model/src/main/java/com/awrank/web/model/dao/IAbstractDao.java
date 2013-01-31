package com.awrank.web.model.dao;

import com.awrank.web.model.domain.AbstractObject;

/**
 * User: a_polyakov
 */
public interface IAbstractDao<T extends AbstractObject> {
    public void persist(T object);

    public void merge(T object);

    public void remove(T object);

    public T find(Class<T> entityClass, Long id);
}
