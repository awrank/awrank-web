package com.awrank.web.model.exception;

import com.awrank.web.model.utils.json.IJsonObject;
import com.awrank.web.model.utils.json.JsonUtils;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

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
    public ObjectNode toJsonObject() {
        final ObjectNode jsonObject = new ObjectNode(JsonNodeFactory.instance);
        JsonUtils.set(jsonObject, "exception", getClass().getCanonicalName());
        JsonUtils.set(jsonObject, "message", getMessage());
        return jsonObject;
    }

    @Override
    public String toString() {
        return toJsonObject().toString();
    }
}





