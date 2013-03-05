package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.AbstractUserRelatedEntityAuditable;
import com.awrank.web.model.enums.Role;

import javax.persistence.*;

/**
 * The class describes domain entity which reflects user roles.
 *
 * @author Alex Polyakov
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "user_roles")
public class UserRole extends AbstractUserRelatedEntityAuditable {

	/**
	 * User role, e.g. ROLE_USER or ROLE_ADMIN.
	 */
	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	public UserRole() {
	}

	public UserRole(User user) {
		this(user, Role.ROLE_USER);
	}
	
	public UserRole(User user, Role role) {
		this.user = user;
		user.getUserRoles().add(this);
		this.role = role;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

}
