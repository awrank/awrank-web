package com.awrank.web.backend.authentication;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Class for providing custom authentication services, based on Spring Security
 * 
 * @author Olga Korokhina
 *
 */
public class AWRaningUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		UserDetails ud = new AWRankingUserDetails();
		//stub for users were prev-ly in config

		
		return null;
	}

}
