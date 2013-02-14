package com.awrank.web.model.exception;

import com.awrank.web.model.constant.EMessageConst;


/**
 *
 */
public class ObjectNotUniqueException extends AwRankModelException {
    private Integer objectAIndex;
    private Long objectAId;
    private Integer objectBIndex;
    private Long objectBId;

    public ObjectNotUniqueException() {
    }

    public ObjectNotUniqueException(Integer objectAIndex, Long objectAId, Integer objectBIndex, Long objectBId) {
        super(EMessageConst.OBJECT_NOT_UNIQUE.name());
      
        this.objectAIndex = objectAIndex;
        this.objectAId = objectAId;
        this.objectBIndex = objectBIndex;
        this.objectBId = objectBId;
    }

}
