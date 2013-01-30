package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.EObjectType;
import com.awrank.web.model.domain.constant.UserLimitConst;
import com.awrank.web.model.utils.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.*;
import java.util.Date;

/**
 * активность пользователей за день
 * расчетное допустимое количество запросов в день
 */
@Entity
@Table(name = UserLimitConst.TABLE_NAME)
public class UserLimit extends AbstractUserItem implements UserLimitConst {

    /**
     * доступное количество запросов
     */
    private Integer availableRequests;
    /**
     * день на который произведен расчет доступного количества
     */
    private Date startedDate;

    {
        objectType = EObjectType.USER_LIMIT;
    }

    public UserLimit() {
    }

    @Column(name = S_AVAILABLE_REQUESTS, nullable = false)
    public Integer getAvailableRequests() {
        return availableRequests;
    }

    public void setAvailableRequests(Integer availableRequests) {
        this.availableRequests = availableRequests;
    }

    @Column(name = S_STARTED_DATE, nullable = false)
    @Temporal(TemporalType.DATE)
    public Date getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(Date startedDate) {
        this.startedDate = startedDate;
    }

    // --------------------------- JSON ------------------------------------------

    public UserLimit(final JsonObject jsonObject) {
        super(jsonObject);
        this.availableRequests = JsonUtils.getInteger(jsonObject, S_AVAILABLE_REQUESTS);
        this.startedDate = JsonUtils.getDate(jsonObject, S_STARTED_DATE);
    }

    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_AVAILABLE_REQUESTS, availableRequests);
        JsonUtils.set(jsonObject, S_STARTED_DATE, startedDate);
        return jsonObject;
    }
}
