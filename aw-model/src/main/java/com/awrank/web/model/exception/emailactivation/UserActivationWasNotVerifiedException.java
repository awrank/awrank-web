package com.awrank.web.model.exception.emailactivation;

import com.awrank.web.model.exception.AwRankException;

/**
 * Thrown in case when {@link com.awrank.web.model.service.EntryPointService#delete(com.awrank.web.model.domain.User)}
 * not performed correctly.
 *
 * @author Olga Korokhina
 */
public class UserActivationWasNotVerifiedException extends AwRankException {

    private final String message = "User email activation was not verified";

    public UserActivationWasNotVerifiedException() {
        super();
    }

    public UserActivationWasNotVerifiedException(String message) {
        super(message);
    }

    public UserActivationWasNotVerifiedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
