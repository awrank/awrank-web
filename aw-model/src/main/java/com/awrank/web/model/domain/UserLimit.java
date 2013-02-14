package com.awrank.web.model.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

/**
 * активность пользователей за день
 * расчетное допустимое количество запросов в день
 */
@Entity
@Table(name = "user_limit")
public class UserLimit extends AbstractPersistable<Long>{

    private Integer availableRequests;
  
    private Date startedDate;
   
    public UserLimit() {
    }

    @Column(name = "available_requests", nullable = false)
    public Integer getAvailableRequests() {
        return availableRequests;
    }

    public void setAvailableRequests(Integer availableRequests) {
        this.availableRequests = availableRequests;
    }

    @Column(name = "started_at", nullable = false)
    @Temporal(TemporalType.DATE)
   
    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

}
