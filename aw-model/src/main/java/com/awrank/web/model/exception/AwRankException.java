package com.awrank.web.model.exception;

/**
 * Base exception to be used in AwRank Web project.
 *
 * @author Andrew Stoyaltsev
 */
public class AwRankException extends Exception {

    public AwRankException() {
        super();
    }

    public AwRankException(String message) {
        super(message);
    }

    public AwRankException(String message, Throwable cause) {
        super(message, cause);
    }

    public AwRankException(Throwable cause) {
        super(cause);
    }

}





