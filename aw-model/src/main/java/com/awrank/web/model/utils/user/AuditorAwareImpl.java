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
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Dummy implementation of {@link AuditorAware}. It will return the configured {@link User} as auditor on every
 * call to {@link #getCurrentAuditor()}. Normally you would access the applications security subsystem to return the
 * current user.
 *
 * @author Eugene Solomka
 * @author Alex Polyakov
 * @author Olga Korokhina
 */
@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<User> {

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Saves a current {@code User} instance to session, based on its entry point data.
	 *
	 * @param entryPoint {@code EntryPoint} instance connected to current {@code User}.
	 */
	public void setCurrentAuditor(EntryPoint entryPoint) {
		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(entryPoint.getUid(), entryPoint.getPassword());
		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
	}

	/**
	 * Saves a current {@code User} instance to session, based on servlet request instance and its credentials:
	 * {@code uid} and {@code password}.
	 *
	 * @param request  servlet request
	 * @param uid      unique identifier of entry point (e.g. email, uid in social network)
	 * @param password user password (todo: what it should be for social network? currently apiKey is used)
	 */
	public void setCurrentAuditor(HttpServletRequest request, String uid, String password) {
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(uid, password);
		token.setDetails(new WebAuthenticationDetails(request));
		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
//		return authenticatedUser.getPrincipal();
	}

	public static AWRankingUserDetails getCurrentUserDetails() {
		AWRankingUserDetails details = null;
		SecurityContext securityContext = SecurityContextHolder.getContext();
		if (securityContext != null) {
			Authentication authentication = securityContext.getAuthentication();
			if (authentication != null) {
				Object o = authentication.getDetails();
				if (o instanceof AWRankingUserDetails) {
					details = (AWRankingUserDetails) o;
				}
				o = authentication.getPrincipal();//"anonymousUser"
				if (o instanceof AWRankingUserDetails) {
					details = (AWRankingUserDetails) o;
				}
			}
		}
		return details;
	}

	/**
	 * Warning user empty, filled only id
	 *
	 * @return current user
	 */
	public User getCurrentAuditor() {
		User user = null;
		AWRankingUserDetails details = getCurrentUserDetails();
		if (details != null) {
			user = new User(details.getUserId());
		}
		return user;
	}
}