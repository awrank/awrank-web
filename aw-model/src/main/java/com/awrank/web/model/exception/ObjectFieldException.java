package com.awrank.web.model.exception;

import com.awrank.web.model.constant.EMessageConst;
import org.codehaus.jackson.node.ObjectNode;

/**
 *
 */
public class ObjectFieldException extends AwRankModelException {
    private Integer objectIndex;
    private Long objectId;
    private String fieldName;

    public ObjectFieldException() {
    }

    public ObjectFieldException(EMessageConst message,Integer objectIndex, Long objectId, String fieldName) {
        super(message.name());
      
        this.objectIndex = objectIndex;
        this.objectId = objectId;
        this.fieldName = fieldName;
    }

}
