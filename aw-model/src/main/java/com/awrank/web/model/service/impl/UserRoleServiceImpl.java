package com.awrank.web.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awrank.web.model.dao.UserRoleDao;
import com.awrank.web.model.domain.UserRole;
import com.awrank.web.model.exception.userrole.*;
import com.awrank.web.model.service.UserRoleService;
import com.awrank.web.model.domain.User;

/**
 * @author Olga Korokhina
 *
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleDao userRoleDao;
	
	@Override
	public void add(UserRole ur) throws UserRoleNotCreatedException {
		
		userRoleDao.save(ur);

	}

	@Override
	public void delete(UserRole ur) throws UserRoleNotDeletedException {
		userRoleDao.delete(ur);

	}

	@Override
	public void save(UserRole ur) {
		userRoleDao.save(ur);

	}

	@Override
	public List<UserRole> findUserRoleForUser(User user) {
		
		return userRoleDao.select(user);
	}

}
