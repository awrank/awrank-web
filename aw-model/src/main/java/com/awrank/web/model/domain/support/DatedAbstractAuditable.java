package com.awrank.web.model.domain.support;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The class extends {@link ExtendedAbstractAuditable} with date properties, which should be mapped to corresponding
 * columns in tables.
 * The initial version of this class (1.0) contains only {@code ended_at} field. But it could be extended with other
 * date field yuo might need.
 *
 * @author Andrew Stoyaltsev
 * @version 1.0
 */
@SuppressWarnings("serial")
@MappedSuperclass
public class DatedAbstractAuditable extends ExtendedAbstractAuditable {

	/**
	 * A date when a table record was finished.
	 * It can be considered as simulation of record removing, or invalidating.
	 */
	@Column(name = "ended_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime endedDate;

    /* getters & setters */

	public LocalDateTime getEndedDate() {
		return endedDate;
	}

	public void setEndedDate(LocalDateTime endedDate) {
		this.endedDate = endedDate;
	}
}
