package com.awrank.web.model.exception;

import com.awrank.web.model.enums.Message;

/**
 * todo: description
 *
 * @author Olga Korokhina
 */
public class ObjectNotUniqueException extends AwRankModelException {

    private Integer objectAIndex;

    private Long objectAId;

    private Integer objectBIndex;

    private Long objectBId;

    public ObjectNotUniqueException() {
        super();
    }

    /**
     *
     * @param objectAIndex
     * @param objectAId
     * @param objectBIndex
     * @param objectBId
     */
    public ObjectNotUniqueException(Integer objectAIndex, Long objectAId, Integer objectBIndex, Long objectBId) {
        super(Message.OBJECT_NOT_UNIQUE.name());

        this.objectAIndex = objectAIndex;
        this.objectAId = objectAId;
        this.objectBIndex = objectBIndex;
        this.objectBId = objectBId;
    }

}
