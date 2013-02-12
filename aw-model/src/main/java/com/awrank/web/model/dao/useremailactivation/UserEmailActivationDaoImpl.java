package com.awrank.web.model.dao.useremailactivation;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.awrank.web.model.dao.AbstractDaoImpl;
import com.awrank.web.model.domain.UserEmailActivation;

/**
 * 
 * @author Olga Korokhina
 *
 */
@Repository
public class UserEmailActivationDaoImpl  extends AbstractDaoImpl<UserEmailActivation> implements UserEmailActivationDao {

	private static final String HQ_SELECT = "select o from " + UserEmailActivation.class.getSimpleName() + " o " +
            "where o." + UserEmailActivation.H_CODE + "=?1";

	@Override
	public UserEmailActivation select(String code) {
		 Query query = em.createQuery(HQ_SELECT);
	        query.setParameter(1, code);
	        UserEmailActivation entry = null;
	        try {
	        	entry = (UserEmailActivation) query.getSingleResult();
	        } catch (NoResultException e) {
	        }
	        return entry;
	}

}
