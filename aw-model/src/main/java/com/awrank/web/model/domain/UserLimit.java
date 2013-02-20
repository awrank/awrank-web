package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.AbstractUserRelatedEntityAuditable;

import javax.persistence.*;
import java.util.Date;

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

    @Column(name = "available_requests", nullable = false)
    private Integer availableRequests;

    @Column(name = "started_at", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startedDate;

    public UserLimit() {
    }

    public Integer getAvailableRequests() {
        return availableRequests;
    }

    public void setAvailableRequests(Integer availableRequests) {
        this.availableRequests = availableRequests;
    }

    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

}
