/**
 * 
 */
package com.awrank.web.backend.authentication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserRoleService;
import com.awrank.web.model.service.UserService;

/**
 * "Wrapper" around User for authrizing it in Spring Security
 * @author Olga Korokhina
 *
 */
@SuppressWarnings("serial")
public class AWRankingUserDetails implements Serializable, UserDetails {
	
	
	private String password;
	
	public void setPassword(String value){
		
		password = value;
	}
	
	public String getPassword(){
		
		return password;
	}
	
	private User user;
		
	public User getUser(){
		
		return user;	
	}
	
	public void setUser(User value){
		
		user = value;
		
	}
	
	private Set<Role> roles;
	
	public Set<Role> getRoles() {

		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	
	}
	
	public AWRankingUserDetails(User user){
		
		this.user = user;
	}
	
	//------- as far as we can have several entry points for same user we need to have login type to fetch the password
	
	private EntryPointType type = EntryPointType.EMAIL;//default value is login
	
	public void setType(EntryPointType value){
		
		type = value;
	}
	
	public EntryPointType getType(){
		
		return type;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getAuthorities()
	 */
	@Override
	/*
	public AWRankingGrantedAuthority[] getAuthorities() {
		
		
		List list = new ArrayList();
		for (Role role : roles) {
		list.add(new AWRankingGrantedAuthority(user.getId(), user.getEmail(), role.toString()));
		}
		return (AWRankingGrantedAuthority[])list.toArray(new GrantedAuthority[0]);
		
	}
	*/
	
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<AWRankingGrantedAuthority> list = new ArrayList<AWRankingGrantedAuthority>();
	
		if(user == null) return list;
		
		for (Role role : roles) {
		
			list.add(new AWRankingGrantedAuthority(user.getId(), user.getEmail(), role.toString()));//email as username
		}
		
		return list;

	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#getUsername()
	 */
	@Override
	public String getUsername() {
	
		if(user == null || type == null) return null;
		if(type == EntryPointType.LOGIN) return user.getEmail();
		if(type == EntryPointType.EMAIL) return user.getEmail();
		return null;
	}

	
	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Override
	public boolean isAccountNonLocked() {
		if(user == null) return false;
		if(user.getBanStartedDate() != null) return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.core.userdetails.UserDetails#isEnabled()
	 */
	@Override
	public boolean isEnabled() {
		
		if(user == null) return false;
		if(user.getBanStartedDate() != null) return false;
		return true;
	}
}