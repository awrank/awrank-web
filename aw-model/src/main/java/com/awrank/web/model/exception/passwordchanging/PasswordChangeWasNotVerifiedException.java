package com.awrank.web.model.exception.passwordchanging;

import com.awrank.web.model.exception.AwRankException;

/**
 * 
 * not performed correctly.
 *
 * @author Olga Korokhina
 */
public class PasswordChangeWasNotVerifiedException extends AwRankException {

    private final String message = "Password change was not verified";

    public PasswordChangeWasNotVerifiedException() {
        super();
    }

    public PasswordChangeWasNotVerifiedException(String message) {
        super(message);
    }

    public PasswordChangeWasNotVerifiedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
