package com.awrank.web.model.dao;

import com.awrank.web.model.domain.AbstractObject;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: a_polyakov
 */
public abstract class AbstractDaoImpl<T extends AbstractObject> implements AbstractDao<T> {
    @PersistenceContext
    protected EntityManager em;

    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    @Override
    public void persist(T object) {
        em.persist(object);
    }

    @Override
    public void merge(T object) {
        em.merge(object);
    }

    @Override
    public void remove(T object) {
        em.remove(object);
    }

    @Override
    public T find(Class<T> entityClass, Long id) {
        return em.find(entityClass, id);
    }
}
