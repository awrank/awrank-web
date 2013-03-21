package com.awrank.web.model.service.jopos;

import com.awrank.web.model.domain.*;
import com.awrank.web.model.enums.Role;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * "Wrapper" around User for authrizing it in Spring Security
 *
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
public class AWRankingUserDetails implements Serializable, UserDetails {

	private final Long entryHistoryId;
	private final String uid;
	private final String password;
	private final EntryPointType entryPointType;
	private final Long userId;
	private final String userEmail;
	private final boolean isAccountNonExpired;
	private final boolean isAccountNonLocked;
	private final boolean isCredentialsNonExpired;
	private final boolean isEnabled;
	private final List<Role> authorities = new ArrayList<Role>();

	public AWRankingUserDetails(EntryHistory entryHistory) {
		this.entryHistoryId = entryHistory.getId();
		final EntryPoint entryPoint = entryHistory.getEntryPoint();
		this.uid = entryPoint.getUid();
		this.password = entryPoint.getPassword();
		this.entryPointType = entryPoint.getType();
		final User user = entryHistory.getUser();
		this.userId = user.getId();
		this.userEmail = user.getEmail();
		for (final UserRole userRole : user.getUserRoles()) {
			authorities.add(userRole.getRole());
		}
		// TODO
		this.isAccountNonExpired = user.getBanStartedDate() == null;
		//TODO
		this.isAccountNonLocked = user.getBanStartedDate() == null;
		// TODO
		this.isCredentialsNonExpired = true;
		// TODO
		this.isEnabled = user.getBanStartedDate() == null;
	}

	public Long getEntryHistoryId() {
		return entryHistoryId;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUid() {
		return uid;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public EntryPointType getEntryPointType() {
		return entryPointType;
	}

	public boolean hasRole(Role role) {
		boolean has = false;
		for (int i = 0; has == false && i < authorities.size(); i++) {
			has = (authorities.get(i) == role);
		}
		return has;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		return uid;
	}

	/* (non-Javadoc)
	* @see org.springframework.security.core.userdetails.UserDetails#getPassword()
	*/
	@Override
	public String getPassword() {
		return password;
	}

	/* (non-Javadoc)
	* @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	*/
	@Override
	public List<Role> getAuthorities() {
		return authorities;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return isAccountNonExpired;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		return isAccountNonLocked;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return isCredentialsNonExpired;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return isEnabled;
	}
}