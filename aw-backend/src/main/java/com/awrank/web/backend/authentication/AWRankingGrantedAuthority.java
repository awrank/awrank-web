package com.awrank.web.backend.authentication;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Olga Korokhina
 *
 */
@SuppressWarnings("serial")
public class AWRankingGrantedAuthority implements GrantedAuthority {

	/* (non-Javadoc)
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	
	public AWRankingGrantedAuthority(Long userId, String authority){
		
		this.userId = userId;
		this.authority = authority;
	}
	
	private Long userId;
	private String authority;//ROLE_USER etc. - not password but role wording
	
	@Override
	public String getAuthority() {
		
		return authority;
	}
	
}
