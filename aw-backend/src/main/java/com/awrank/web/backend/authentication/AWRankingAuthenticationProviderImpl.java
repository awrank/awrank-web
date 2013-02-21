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

/**
 * Class for providing custom authentication provider, based on Spring Security
 * 
 * @author Olga Korokhina
 *
 */
@Component 
public class AWRankingAuthenticationProviderImpl extends
		AbstractUserDetailsAuthenticationProvider {

	@Autowired
	private ApplicationContext appContext;
	
	//@Autowired
	private AWRankingUserDetailsService userDetailsService;
	
	public void setUserDetailsService(AWRankingUserDetailsService value){
		
		userDetailsService = value;
		
	}
	
	public AWRankingUserDetailsService getUserDetailsService(){
		
		
		if(userDetailsService ==  null && appContext != null){
			userDetailsService = (AWRankingUserDetailsService) appContext.getBean("userDetailsService");
		}
		
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
		
		getUserDetailsService().loadUserByUsername(username);
		return null;
	}

}
