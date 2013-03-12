package com.awrank.web.model.domain.support;

import com.awrank.web.model.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 * Extension of {@link ExtendedAbstractAuditable} class which provides association of {@code User} instance
 * with other entities via {@code user_id} column.
 *
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class AbstractUserRelatedEntityAuditable extends DatedAbstractAuditable {

	/**
	 * User associated with record.
	 */
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id", nullable = false, updatable = false)
	protected User user;

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
