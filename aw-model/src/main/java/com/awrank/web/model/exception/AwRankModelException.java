package com.awrank.webapp.model.exception;

/**
 * Base exception to be used in AwRank Web project.
 *
 * @author Andrew Stoyaltsev
 */
public class AwRankModelException extends Exception {

    public AwRankModelException() {
        super();
    }

    public AwRankModelException(String message) {
        super(message);
    }

    public AwRankModelException(String message, Throwable cause) {
        super(message, cause);
    }

    public AwRankModelException(Throwable cause) {
        super(cause);
    }

}





