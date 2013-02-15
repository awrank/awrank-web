package com.awrank.web.model.exception;

import com.awrank.web.model.enums.Message;
import org.springframework.data.jpa.domain.AbstractAuditable;

/**
 * Error associated with the value of the field entity
 *
 * @author Olga Korokhina
 */
public class ObjectFieldException extends AwRankModelException {
	/**
	 * Class entity
	 */
	private Class<? extends AbstractAuditable> objectClass;

	/**
	 * Index of entity in the set (necessary when the entity has not been preserved and there is no id)
	 */
	private Integer objectIndex;

	/**
	 * Id entity in which the error occurred
	 */
	private Long objectId;

	/**
	 * Hibernate field name
	 */
	private String fieldName;

	/**
	 * Error associated with the value of the field entity
	 *
	 * @param message
	 * @param objectClass
	 * @param objectIndex
	 * @param objectId
	 * @param fieldName
	 */
	public ObjectFieldException(Message message, Class<? extends AbstractAuditable> objectClass, Integer objectIndex, Long objectId, String fieldName) {
		super(message.name());
		this.objectClass = objectClass;
		this.objectIndex = objectIndex;
		this.objectId = objectId;
		this.fieldName = fieldName;
	}

}
