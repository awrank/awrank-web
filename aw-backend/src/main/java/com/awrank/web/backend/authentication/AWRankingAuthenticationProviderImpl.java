package com.awrank.web.backend.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Qualifier;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserRoleService;
import com.awrank.web.model.service.UserService;
import com.awrank.web.model.utils.user.PasswordUtils;

/**
 * Class for providing custom authentication provider, based on Spring Security
 * 
 * @author Olga Korokhina
 *
 */
@Component 
public class AWRankingAuthenticationProviderImpl extends
		AbstractUserDetailsAuthenticationProvider {

	
	//@Autowired
	private AWRankingUserDetailsService userDetailsService;
	
	public void setUserDetailsService(AWRankingUserDetailsService value){
		
		userDetailsService = value;
		
	}
	
	public AWRankingUserDetailsService getUserDetailsService(){
	
		return userDetailsService;
	}
	
	public AWRankingAuthenticationProviderImpl(){
		
		
	}
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#additionalAuthenticationChecks(org.springframework.security.core.userdetails.UserDetails, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#retrieveUser(java.lang.String, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
	 */
	@Override
	protected UserDetails retrieveUser(String username,
			UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		
		return getUserDetailsService().loadUserByUsernameAndPassword(username, PasswordUtils.hashPassword(String.valueOf(authentication.getCredentials())));
		
	}

}
