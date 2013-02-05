package com.awrank.web.backend.exception;

import com.awrank.web.model.exception.AwRankException;
import com.awrank.web.model.utils.json.IJsonObject;

/**
 * Base exception to be used in controller AwRank Web project.
 *
 * @author Andrew Stoyaltsev
 */
public class AwRankControllerException extends AwRankException implements IJsonObject {

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





