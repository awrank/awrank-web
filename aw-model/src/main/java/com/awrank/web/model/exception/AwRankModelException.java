package com.awrank.web.model.exception;

/**
 * Base exception to be used in model AwRank Web project.
 *
 * @author Andrew Stoyaltsev
 */
public class AwRankModelException extends AwRankException {

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





