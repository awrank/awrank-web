package com.awrank.web.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.userrole.*;
import com.awrank.web.model.domain.UserRole;

/**
 * @author Olga Korokhina
 * Interface for REST service working with user roles
 */
@Service
public interface UserRoleService {
	
	public void add(UserRole ep) throws UserRoleNotCreatedException;
	
	public void delete(UserRole ep) throws UserRoleNotDeletedException;
	
	public void save(UserRole ep);
	
	public List<UserRole> findUserRoleForUser(User user);
	
	
}
