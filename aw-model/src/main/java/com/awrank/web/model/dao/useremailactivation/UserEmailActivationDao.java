package com.awrank.web.model.dao.useremailactivation;

import org.springframework.stereotype.Repository;

import com.awrank.web.model.dao.AbstractDao;
import com.awrank.web.model.domain.UserEmailActivation;
/**
 * 
 * @author Olga Korokhina
 *
 */
@Repository
public interface UserEmailActivationDao extends AbstractDao<UserEmailActivation> {
	
	public UserEmailActivation select(String code);

}
