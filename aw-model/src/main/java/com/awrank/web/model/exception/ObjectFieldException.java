package com.awrank.web.model.exception;

import com.awrank.web.model.enums.Message;

/**
 * todo: description
 *
 * @author Olga Korokhina
 */
public class ObjectFieldException extends AwRankModelException {

    /**
     * todo: ?
     */
    private Integer objectIndex;

    /**
     * todo: ?
     */
    private Long objectId;

    /**
     * todo: ?
     */
    private String fieldName;

    public ObjectFieldException() {
        super();
    }

    /**
     * todo:
     * @param message
     * @param objectIndex
     * @param objectId
     * @param fieldName
     */
    public ObjectFieldException(Message message, Integer objectIndex, Long objectId, String fieldName) {
        super(message.name());

        this.objectIndex = objectIndex;
        this.objectId = objectId;
        this.fieldName = fieldName;
    }

}
