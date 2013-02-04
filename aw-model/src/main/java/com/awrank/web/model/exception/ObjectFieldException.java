package com.awrank.web.model.exception;

import com.awrank.web.model.constant.EMessageConst;
import com.awrank.web.model.domain.constant.EObjectType;
import com.awrank.web.model.utils.json.JsonUtils;
import org.codehaus.jackson.node.ObjectNode;

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

    public ObjectNode toJsonObject() {
        final ObjectNode jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, "objectType", objectType.name());
        JsonUtils.set(jsonObject, "objectIndex", objectIndex);
        JsonUtils.set(jsonObject, "objectId", objectId);
        JsonUtils.set(jsonObject, "fieldName", fieldName);
        return jsonObject;
    }
}
