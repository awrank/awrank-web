package com.awrank.web.model.exception.userrole;

import com.awrank.web.model.exception.AwRankException;

/**
 * Thrown in case when {@link com.awrank.web.model.service.UserRoleService#delete(com.awrank.web.model.domain.User)}
 * not performed correctly.
 *
 * @author Olga Korokhina
 */
public class UserRoleNotDeletedException extends AwRankException {

    private final String message = "UserRole was not deleted!";

    public UserRoleNotDeletedException() {
        super();
    }

    public UserRoleNotDeletedException(String message) {
        super(message);
    }

    public UserRoleNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
