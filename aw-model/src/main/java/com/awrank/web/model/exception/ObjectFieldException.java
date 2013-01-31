package com.awrank.web.model.exception;

import com.awrank.web.model.constant.EMessageConst;
import com.awrank.web.model.domain.constant.EObjectType;
import com.google.gson.JsonObject;

/**
 *
 */
public class ObjectFieldException extends AwRankModelException {
    private EObjectType objectType;
    private Integer objectIndex;
    private Long objectId;
    private String fieldName;

    public ObjectFieldException(EMessageConst message, EObjectType objectType, Integer objectIndex, Long objectId, String fieldName) {
        super(message.name());
        this.objectType = objectType;
        this.objectIndex = objectIndex;
        this.objectId = objectId;
        this.fieldName = fieldName;
    }

// --------------------------- JSON ------------------------------------------

    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        jsonObject.addProperty("objectType", objectType.name());
        jsonObject.addProperty("objectIndex", objectIndex);
        jsonObject.addProperty("objectId", objectId);
        jsonObject.addProperty("fieldName", fieldName);
        return jsonObject;
    }
}
