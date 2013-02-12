package com.awrank.web.model.utils.user;

import com.awrank.web.model.domain.User;
import org.springframework.data.domain.AuditorAware;

/**
 * Dummy implementation of {@link AuditorAware}. It will return the configured {@link User} as auditor on every
 * call to {@link #getCurrentAuditor()}. Normally you would access the applications security subsystem to return the
 * current user.
 *
 * @author Oliver Gierke
 */
public class AuditorAwareImpl implements AuditorAware<User> {

    private User auditor;

    /**
     * @param auditor the auditor to set
     */
    public void setAuditor(User auditor) {
        this.auditor = auditor;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.AuditorAware#getCurrentAuditor()
     */
    public User getCurrentAuditor() {
        return auditor;
    }
}