package com.awrank.web.backend.exception;

import com.awrank.web.model.exception.AwRankException;

/**
 * Base exception to be used in controller AwRank Web project.
 *
 * @author Alex Polyakov
 */
public class AwRankControllerException extends AwRankException {

    public AwRankControllerException() {
        super();
    }

    public AwRankControllerException(String message) {
        super(message);
    }

    public AwRankControllerException(String message, Throwable cause) {
        super(message, cause);
    }

    public AwRankControllerException(Throwable cause) {
        super(cause);
    }
}





