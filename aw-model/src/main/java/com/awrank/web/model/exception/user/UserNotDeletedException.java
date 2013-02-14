package com.awrank.web.model.exception.user;

import com.awrank.web.model.exception.AwRankException;

/**
 * Thrown in case when {@link com.awrank.web.model.service.UserService#delete(com.awrank.web.model.domain.User)}
 * not performed correctly.
 *
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 */
public class UserNotDeletedException extends AwRankException {

    private final String message = "User was not deleted!";

    public UserNotDeletedException() {
        super();
    }

    public UserNotDeletedException(String message) {
        super(message);
    }

    public UserNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
