package com.awrank.web.model.service.jopos;

import com.awrank.web.model.enums.Role;
import org.springframework.security.core.GrantedAuthority;

/**
 * "Wrapper" around UserRole
 *
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
public class AWRankingGrantedAuthority implements GrantedAuthority {
	/**
	 * User role
	 */
	private Role role;

	/* (non-Javadoc)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	public AWRankingGrantedAuthority(Role role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		return role.name();
	}

}
