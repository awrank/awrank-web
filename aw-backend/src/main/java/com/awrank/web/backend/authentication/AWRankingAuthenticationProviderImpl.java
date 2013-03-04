package com.awrank.web.backend.authentication;

import com.awrank.web.model.service.UserDetailsService;
import com.awrank.web.model.utils.user.PasswordUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Class for providing custom authentication provider, based on Spring Security
 *
 * @author Olga Korokhina
 */
@Controller("awRankingAuthenticationProvider")
public class AWRankingAuthenticationProviderImpl extends AbstractUserDetailsAuthenticationProvider {

	@Resource(name = "userDetailsServiceImpl")
	private UserDetailsService userDetailsService;

	public AWRankingAuthenticationProviderImpl() {
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#additionalAuthenticationChecks(org.springframework.security.core.userdetails.UserDetails, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
	 */
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#retrieveUser(java.lang.String, org.springframework.security.authentication.UsernamePasswordAuthenticationToken)
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {

		return userDetailsService.retrieveUser(username, PasswordUtils.hashPassword((String) authentication.getCredentials()),
				((WebAuthenticationDetails) (authentication.getDetails())).getRemoteAddress(),
				((WebAuthenticationDetails) (authentication.getDetails())).getSessionId());
	}
}
