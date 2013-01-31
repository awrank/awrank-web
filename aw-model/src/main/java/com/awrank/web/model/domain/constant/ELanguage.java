package com.awrank.web.model.domain.constant;

import com.awrank.web.model.utils.json.IJsonObject;
import com.google.gson.JsonObject;

/**
 * User: a_polyakov
 */
public enum ELanguage implements IJsonObject {
    EN,
    RU;

    @Override
    public JsonObject toJsonObject() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("name", name());
        return jsonObject;
    }
}
