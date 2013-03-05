package com.awrank.web.model.utils.user;

import com.awrank.web.model.domain.User;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Dummy implementation of {@link AuditorAware}. It will return the configured {@link User} as auditor on every
 * call to {@link #getCurrentAuditor()}. Normally you would access the applications security subsystem to return the
 * current user.
 *
 * @author Oliver Gierke
 */
public class AuditorAwareImpl implements AuditorAware<User> {

	public User getCurrentAuditor() {
		AWRankingUserDetails details = (AWRankingUserDetails) SecurityContextHolder.getContext().getAuthentication().getDetails();
		return details.getUser();
	}
}