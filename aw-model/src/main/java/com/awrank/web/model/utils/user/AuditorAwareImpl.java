package com.awrank.web.model.utils.user;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Dummy implementation of {@link AuditorAware}. It will return the configured {@link User} as auditor on every
 * call to {@link #getCurrentAuditor()}. Normally you would access the applications security subsystem to return the
 * current user.
 *
 * @author Oliver Gierke
 */
@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<User> {

	@Autowired
	private AuthenticationManager authenticationManager;

	public void setCurrentAuditor(EntryPoint entryPoint) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(entryPoint.getUid(), entryPoint.getPassword());
		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}

	public User getCurrentAuditor() {
		User user = null;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null) {
			Authentication authentication = securityContext.getAuthentication();
			if (authentication != null) {
				Object o = authentication.getDetails();
				if (o instanceof AWRankingUserDetails) {
					AWRankingUserDetails details = (AWRankingUserDetails) o;
					user = details.getUser();
				}
			}
		}
		return user;
	}
}