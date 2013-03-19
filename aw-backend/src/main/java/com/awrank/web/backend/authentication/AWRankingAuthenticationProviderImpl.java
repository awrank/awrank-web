package com.awrank.web.backend.authentication;

import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.service.UserDetailsService;
import com.awrank.web.model.utils.user.PasswordUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Class for providing custom authentication provider, based on Spring Security
 *
 * @author Olga Korokhina
 */
@Component("awRankingAuthenticationProvider")
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
	public UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		String password = (String) authentication.getCredentials();
		String userIpAddress = "";
		String sessionId = "";
		// TODO
		String browseName = "";
		Object currentDetails = authentication.getDetails();
		if (currentDetails instanceof WebAuthenticationDetails) {
			WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) currentDetails;
			userIpAddress = webAuthenticationDetails.getRemoteAddress();
			sessionId = webAuthenticationDetails.getSessionId();
			password = PasswordUtils.hashPassword(password);
		}
		UserDetails details = userDetailsService.retrieveUser(username, password, userIpAddress, sessionId, browseName);
		if (details == null) {
			throw new AuthenticationServiceException("", UnauthorizedException.getInstance());
		}
		return details;
	}
}
