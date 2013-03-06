package com.awrank.web.model.service.jopos;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
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

	private final Long userId;
	private final String uid;
	private final String userEmail;
	private final EntryPointType entryPointType;
	private final boolean isAccountNonExpired;
	private final boolean isAccountNonLocked;
	private final boolean isCredentialsNonExpired;
	private final boolean isEnabled;
	//	private final User user;
	private final String password;
	private final List<AWRankingGrantedAuthority> authorities = new ArrayList<AWRankingGrantedAuthority>();

	public AWRankingUserDetails(EntryPoint entryPoint) {
		this.uid = entryPoint.getUid();
		this.password = entryPoint.getPassword();
		this.entryPointType = entryPoint.getType();

		User user = entryPoint.getUser();
		this.userId = user.getId();
		this.userEmail = user.getEmail();
		for (UserRole userRole : user.getUserRoles()) {
			authorities.add(new AWRankingGrantedAuthority(userRole.getRole()));
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
//
//	public User getUser() {
//		return user;
//	}


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
	public List<AWRankingGrantedAuthority> getAuthorities() {
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