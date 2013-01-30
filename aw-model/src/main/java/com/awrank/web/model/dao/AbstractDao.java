package com.awrank.web.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 */
public abstract class AbstractDao {
    @PersistenceContext
    protected EntityManager em;
}
