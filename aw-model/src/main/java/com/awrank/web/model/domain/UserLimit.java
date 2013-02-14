package com.awrank.web.model.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

/**
 * daily user activity: available requests per day
 * 
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "user_limit")
public class UserLimit extends AbstractPersistable<Long>{

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
