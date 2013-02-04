package com.awrank.web.backend.exception;

import com.awrank.web.model.utils.json.JsonUtils;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * User: a_polyakov
 * <p/>
 * Authentication error or a timeout session
 * Ошибка аутентификации или таймаут сессии
 */
public class UnauthorizedException extends Exception {

    private UnauthorizedException() {
        super("Unauthorized");
    }

    public ObjectNode toJsonObject() {
        final ObjectNode jsonObject = new ObjectNode(JsonNodeFactory.instance);
        JsonUtils.set(jsonObject, "exception", getClass().getCanonicalName());
        JsonUtils.set(jsonObject, "message", getMessage());
        return jsonObject;
    }

    @Override
    public final String toString() {
        return toJsonObject().toString();
    }

    private static UnauthorizedException instance = new UnauthorizedException();

    public static UnauthorizedException getInstance() {
        return instance;
    }
}
