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
public class UserLimit extends AbstractUserRelatedEntityAuditable<Long> {

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
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime startedDate;

    public UserLimit() {
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

}
