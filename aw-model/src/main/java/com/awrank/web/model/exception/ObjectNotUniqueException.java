package com.awrank.web.model.exception;

import com.awrank.web.model.enums.Message;
import org.springframework.data.jpa.domain.AbstractAuditable;

/**
 * entity with the same key already exists or constraint violated
 *
 * @author Olga Korokhina
 */
public class ObjectNotUniqueException extends AwRankModelException {

	/**
	 * Class entity
	 */
	private Class<? extends AbstractAuditable> objectClass;
	/**
	 * Index this entity in the set (necessary when the entity has not been preserved and there is no id)
	 */
	private Integer objectAIndex;
	/**
	 * Id this entity in which the error occurred
	 */
	private Long objectAId;
	/**
	 * Index exist entity in the set (necessary when the entity has not been preserved and there is no id)
	 */
	private Integer objectBIndex;
	/**
	 * Id exist entity in which the error occurred
	 */
	private Long objectBId;

	/**
	 * entity with the same key already exists or constraint violated
	 *
	 * @param objectAIndex
	 * @param objectAId
	 * @param objectBIndex
	 * @param objectBId
	 */
	public ObjectNotUniqueException(Class<? extends AbstractAuditable> objectClass, Integer objectAIndex, Long objectAId, Integer objectBIndex, Long objectBId) {
		super(Message.OBJECT_NOT_UNIQUE.name());
		this.objectClass = objectClass;
		this.objectAIndex = objectAIndex;
		this.objectAId = objectAId;
		this.objectBIndex = objectBIndex;
		this.objectBId = objectBId;
	}

}
