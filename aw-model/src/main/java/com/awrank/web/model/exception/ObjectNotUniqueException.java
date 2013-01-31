package com.awrank.web.model.exception;

import com.awrank.web.model.constant.EMessageConst;
import com.awrank.web.model.domain.constant.EObjectType;
import com.google.gson.JsonObject;

/**
 *
 */
public class ObjectNotUniqueException extends AwRankModelException {
    private EObjectType objectType;
    private Integer objectAIndex;
    private Long objectAId;
    private Integer objectBIndex;
    private Long objectBId;

    public ObjectNotUniqueException(EObjectType objectType, Integer objectAIndex, Long objectAId, Integer objectBIndex, Long objectBId) {
        super(EMessageConst.OBJECT_NOT_UNIQUE.name());
        this.objectType = objectType;
        this.objectAIndex = objectAIndex;
        this.objectAId = objectAId;
        this.objectBIndex = objectBIndex;
        this.objectBId = objectBId;
    }

// --------------------------- JSON ------------------------------------------

    @Override
    public JsonObject toJsonObject() {
        JsonObject jsonObject = super.toJsonObject();
        jsonObject.addProperty("objectType", objectType.name());
        jsonObject.addProperty("objectAIndex", objectAIndex);
        jsonObject.addProperty("objectAId", objectAId);
        jsonObject.addProperty("objectBIndex", objectBIndex);
        jsonObject.addProperty("objectBId", objectBId);
        return jsonObject;
    }
}
