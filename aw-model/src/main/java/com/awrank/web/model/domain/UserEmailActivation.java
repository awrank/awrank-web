package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.EObjectType;
import com.awrank.web.model.domain.constant.UserEmailActivationConst;
import com.awrank.web.model.utils.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.*;
import java.util.Date;

/**
 * подтверждение email пользователя
 */
@Entity
@Table(name = UserEmailActivationConst.TABLE_NAME)
public class UserEmailActivation extends AbstractUserItem implements UserEmailActivationConst {

    /**
     * код для подтверждения
     */
    private String code;
    /**
     * ip адресс с которого начата процедура смены или добавления email
     */
    private String ipAddress;
    /**
     * новый email
     */
    private String email;
    /**
     * дата подтверждения email
     */
    private Date emailVerifiedDate;

    {
        objectType = EObjectType.USER_EMAIL_ACTIVATION;
    }

    public UserEmailActivation() {
    }

    @Column(name = S_CODE, nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = S_IP_ADDRESS, nullable = false)
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Column(name = S_EMAIL, nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = S_EMAIL_VERIFIED_DATE, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEmailVerifiedDate() {
        return emailVerifiedDate;
    }

    public void setEmailVerifiedDate(Date emailVerifiedDate) {
        this.emailVerifiedDate = emailVerifiedDate;
    }

    // --------------------------- JSON ------------------------------------------

    public UserEmailActivation(final JsonObject jsonObject) {
        super(jsonObject);
        this.code = JsonUtils.getString(jsonObject, S_CODE);
        this.ipAddress = JsonUtils.getString(jsonObject, S_IP_ADDRESS);
        this.email = JsonUtils.getString(jsonObject, S_EMAIL);
        this.emailVerifiedDate = JsonUtils.getDate(jsonObject, S_EMAIL_VERIFIED_DATE);
    }

    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_CODE, code);
        JsonUtils.set(jsonObject, S_IP_ADDRESS, ipAddress);
        JsonUtils.set(jsonObject, S_EMAIL, email);
        JsonUtils.set(jsonObject, S_EMAIL_VERIFIED_DATE, emailVerifiedDate);
        return jsonObject;
    }
}