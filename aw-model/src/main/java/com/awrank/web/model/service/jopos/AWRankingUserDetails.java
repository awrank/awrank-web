package com.awrank.web.model.service.jopos;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.UserRole;
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

	private final User user;
	private final String password;
	private final List<AWRankingGrantedAuthority> authorities = new ArrayList<AWRankingGrantedAuthority>();

	public AWRankingUserDetails(EntryPoint entryPoint) {
		this.user = entryPoint.getUser();
		this.password = entryPoint.getPassword();
		for (UserRole userRole : user.getUserRoles()) {
			authorities.add(new AWRankingGrantedAuthority(userRole.getRole()));
		}
	}

	public User getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public List<AWRankingGrantedAuthority> getAuthorities() {
		return authorities;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
		String username = null;
		if (user != null) username = user.getEmail();
		return username;
	}


	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		return (user != null && user.getBanStartedDate() == null);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		return (user != null && user.getBanStartedDate() == null);
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		return (user != null && user.getBanStartedDate() == null);
	}
}