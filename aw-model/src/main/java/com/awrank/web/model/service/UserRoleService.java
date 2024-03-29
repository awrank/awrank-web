package com.awrank.web.model.service;

import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.UserRole;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.exception.userrole.UserRoleNotCreatedException;

import java.util.List;
import java.util.Set;

/**
 * @author Olga Korokhina
 *         Interface for REST service working with user roles
 */
public interface UserRoleService extends AbstractService {

	public void add(UserRole ep) throws UserRoleNotCreatedException;

	public void delete(UserRole ep);

	public void save(UserRole ep);

	public List<UserRole> findUserRoleForUser(User user);

	public Set<Role> findUserRolesSetForUser(User user);
}
