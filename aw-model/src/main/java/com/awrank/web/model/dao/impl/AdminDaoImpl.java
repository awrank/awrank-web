package com.awrank.web.model.dao.impl;

import com.awrank.web.model.service.impl.pojos.UserSocialRegistrationFormPojo;
import com.awrank.web.model.utils.select.SelectUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Temporary class
 *
 * @author Andrew Stoyaltsev
 */
@Repository
public class AdminDaoImpl extends AbstractDaoImpl {

	/* analogue of getAll() */
	public List<UserSocialRegistrationFormPojo> getAllUsers() {
		return SelectUtils.getWrapperList(em, UserSocialRegistrationFormPojo.class, "", 0, 0);
	}

}
