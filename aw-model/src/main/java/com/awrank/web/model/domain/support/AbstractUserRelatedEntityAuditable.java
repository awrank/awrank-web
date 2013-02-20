package com.awrank.web.model.domain.support;

import com.awrank.web.model.domain.User;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Extension of {@link ExtendedAbstractAuditable} class which provides association of {@code User} instance
 * with other entities via {@code user_id} column.
 *
 * @param <ID> Generic type of auditable column. See PK in {@link org.springframework.data.jpa.domain.AbstractAuditable}
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class AbstractUserRelatedEntityAuditable<ID extends Serializable> extends DatedAbstractAuditable<ID> {

	/**
	 * User associated with record.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
