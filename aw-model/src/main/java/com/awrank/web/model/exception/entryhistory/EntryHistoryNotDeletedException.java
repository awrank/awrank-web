package com.awrank.web.model.exception.entryhistory;

import com.awrank.web.model.exception.AwRankException;

/**
 * Thrown in case when {@link com.awrank.web.model.service.EntryHistoryService#delete(com.awrank.web.model.domain.User)}
 * not performed correctly.
 *
 * @author Olga Korokhina
 */
public class EntryHistoryNotDeletedException extends AwRankException {

    private final String message = "Entry History record was not deleted!";

    public EntryHistoryNotDeletedException() {
        super();
    }

    public EntryHistoryNotDeletedException(String message) {
        super(message);
    }

    public EntryHistoryNotDeletedException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return message;
    }
}
