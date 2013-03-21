package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.AbstractUserRelatedEntityAuditable;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

/**
 * The {@code UserLimit} entry represents daily user activity. In other words, it shows available requests per day.
 *
 * @author Alex Polyakov
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "user_limit")
public class UserLimit extends AbstractUserRelatedEntityAuditable {

	/**
	 * Order to which the limit is bound.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id", nullable = false, updatable = false)
	private Order order;

	/**
	 * A quantity of allowed request per day.
	 */
	@Column(name = "available_requests", nullable = false)
	private Integer availableRequests;

	/**
	 * A day when calculation of request quantity was performed.
	 */
	@Column(name = "started_at", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime startedDate;

	@Column(name = "user_limit_type", nullable = false, updatable = false, length = 10)
	@Enumerated(EnumType.STRING)
	private UserLimitType userLimitType;

	/**
	 * For tariffs use limit count request per day and per month
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "user_limit_month_id", nullable = true, updatable = false)
	private UserLimit limitMonth;

	public UserLimit() {
	}


	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getAvailableRequests() {
		return availableRequests;
	}

	public void setAvailableRequests(Integer availableRequests) {
		this.availableRequests = availableRequests;
	}

	public LocalDateTime getStartedDate() {
		return startedDate;
	}

	public void setStartedDate(LocalDateTime startedDate) {
		this.startedDate = startedDate;
	}

	public UserLimitType getUserLimitType() {
		return userLimitType;
	}

	public void setUserLimitType(UserLimitType userLimitType) {
		this.userLimitType = userLimitType;
	}

	public UserLimit getLimitMonth() {
		return limitMonth;
	}

	public void setLimitMonth(UserLimit limitMonth) {
		this.limitMonth = limitMonth;
	}
}
