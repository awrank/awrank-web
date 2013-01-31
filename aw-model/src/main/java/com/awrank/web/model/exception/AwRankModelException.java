package com.awrank.web.model.exception;

import com.awrank.web.model.utils.json.IJsonObject;
import com.google.gson.JsonObject;

/**
 * Base exception to be used in AwRank Web project.
 *
 * @author Andrew Stoyaltsev
 */
public class AwRankModelException extends Exception implements IJsonObject {

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

// --------------------------- JSON ------------------------------------------

    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("exception", getClass().getCanonicalName());
        jsonObject.addProperty("message", getMessage());
        return jsonObject;
    }

    @Override
    public String toString() {
        return toJsonObject().toString();
    }
}





