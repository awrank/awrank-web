package com.awrank.web.model.exception;

import com.awrank.web.model.utils.json.IJsonObject;
import com.awrank.web.model.utils.json.JsonUtils;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Base exception to be used in AwRank Web project.
 *
 * @author Andrew Stoyaltsev
 */
public class AwRankException extends Exception implements IJsonObject {

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

// --------------------------- JSON ------------------------------------------

    @Override
    public ObjectNode toJsonObject() {
        final ObjectNode jsonObject = new ObjectNode(JsonNodeFactory.instance);
        JsonUtils.set(jsonObject, "exception", getClass().getCanonicalName());
        JsonUtils.set(jsonObject, "message", getMessage());
        StringWriter writer = new StringWriter();
        printStackTrace(new PrintWriter(writer));
        JsonUtils.set(jsonObject, "stackTrace", writer.toString());
        return jsonObject;
    }
}





