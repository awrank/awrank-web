package com.awrank.web.model.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * User: a_polyakov
 */
public class AbstractDaoImpl {
	@PersistenceContext
	protected EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}
}
