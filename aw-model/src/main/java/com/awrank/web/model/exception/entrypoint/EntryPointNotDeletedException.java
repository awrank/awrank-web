package com.awrank.web.model.exception.entrypoint;

import com.awrank.web.model.exception.AwRankException;

/**
 * Thrown in case when {@link com.awrank.web.model.service.EntryPointService#delete(com.awrank.web.model.domain.User)}
 * not performed correctly.
 *
 * @author Olga Korokhina
 */
public class EntryPointNotDeletedException extends AwRankException {

    private final String message = "Entry Point was not deleted!";

    public EntryPointNotDeletedException() {
        super();
    }

    public EntryPointNotDeletedException(String message) {
        super(message);
    }

    public EntryPointNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
