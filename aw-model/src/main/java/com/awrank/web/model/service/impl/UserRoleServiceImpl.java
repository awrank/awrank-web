package com.awrank.web.model.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.awrank.web.model.dao.UserRoleDao;
import com.awrank.web.model.domain.UserRole;
import com.awrank.web.model.enums.Role;
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
	@Qualifier("userRoleDao")
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

	@Override
	public Set<Role> findUserRolesSetForUser(User user) {
		
		Set<Role> set = new HashSet<Role>();
		List<UserRole> list = userRoleDao.select(user);
		
		//It shall be more beautiful way with Collections here but I can't recall it now
		for (UserRole ur : list) { set.add(ur.getRole()); }
		
		return set;
	}

}
