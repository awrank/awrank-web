package com.awrank.web.model.exception;

import com.awrank.web.model.constant.EMessageConst;
import com.awrank.web.model.domain.constant.EObjectType;
import com.awrank.web.model.utils.json.JsonUtils;
import org.codehaus.jackson.node.ObjectNode;

/**
 *
 */
public class ObjectNotUniqueException extends AwRankModelException {
    private EObjectType objectType;
    private Integer objectAIndex;
    private Long objectAId;
    private Integer objectBIndex;
    private Long objectBId;

    public ObjectNotUniqueException() {
    }

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
    public ObjectNode toJsonObject() {
        ObjectNode jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, "objectType", objectType.name());
        JsonUtils.set(jsonObject, "objectAIndex", objectAIndex);
        JsonUtils.set(jsonObject, "objectAId", objectAId);
        JsonUtils.set(jsonObject, "objectBIndex", objectBIndex);
        JsonUtils.set(jsonObject, "objectBId", objectBId);
        return jsonObject;
    }
}
