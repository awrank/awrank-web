package com.awrank.web.data;

import com.awrank.web.data.constant.UserLimitConst;
import com.awrank.web.utils.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.*;
import java.util.Date;

/**
 * активность пользователей за день
 * расчетное допустимое количество запросов в день
 */
@Entity
@Table(name = UserLimitConst.TABLE_NAME)
public class UserLimit extends AbstractObject implements UserLimitConst {
    /**
     * пользователь
     */
    private User user;
    /**
     * доступное количество запросов
     */
    private Integer availableRequests;
    /**
     * день на который произведен расчет доступного количества
     */
    private Date started;

    public UserLimit() {
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = S_USER, nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = S_AVAILABLE_REQUESTS, nullable = false)
    public Integer getAvailableRequests() {
        return availableRequests;
    }

    public void setAvailableRequests(Integer availableRequests) {
        this.availableRequests = availableRequests;
    }

    @Column(name = S_STARTED, nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getStarted() {
        return started;
    }

    public void setStarted(Date started) {
        this.started = started;
    }

    // --------------------------- JSON ------------------------------------------

    public UserLimit(final JsonObject jsonObject) {
        super(jsonObject);
        // user
        this.availableRequests = JsonUtils.getInteger(jsonObject, S_AVAILABLE_REQUESTS);
        this.started = JsonUtils.getDate(jsonObject, S_STARTED);
    }

    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_USER, user);
        JsonUtils.set(jsonObject, S_AVAILABLE_REQUESTS, availableRequests);
        JsonUtils.set(jsonObject, S_STARTED, started);
        return jsonObject;
    }
}
