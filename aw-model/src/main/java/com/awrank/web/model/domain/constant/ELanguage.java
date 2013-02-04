package com.awrank.web.model.domain.constant;

import com.awrank.web.model.utils.json.IJsonObject;
import com.awrank.web.model.utils.json.JsonUtils;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * User: a_polyakov
 */
public enum ELanguage implements IJsonObject {
    EN,
    RU;

    @Override
    public ObjectNode toJsonObject() {
        final ObjectNode jsonObject = new ObjectNode(JsonNodeFactory.instance);
        JsonUtils.set(jsonObject, "name", name());
        return jsonObject;
    }
}
