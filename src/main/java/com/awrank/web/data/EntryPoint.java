package com.awrank.web.data;

import com.awrank.web.data.constant.EAuthenticationMethod;
import com.awrank.web.data.constant.EntryPointConst;
import com.awrank.web.utils.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.*;
import java.util.Date;

/**
 * точка входа
 */
@Entity
@Table(name = EntryPointConst.TABLE_NAME)
public class EntryPoint extends AbstractUserItem implements EntryPointConst {

    /**
     * Логин или email или идентификатор в социальной сети
     */
    private String uid;
    /**
     * пароль входа
     * используется при входе по логину или email
     * для шифрования SHA-2 + соль
     */
    private String password;
    /**
     * дата подтверждения
     */
    private Date verified;
    /**
     * способ аутентификации (email, google, facebook...)
     */
    private EAuthenticationMethod authenticationMethod;

    public EntryPoint() {
    }

    @Column(name = S_UID, nullable = false)
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Column(name = S_PASSWORD, nullable = true)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = S_VERIFIED, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getVerified() {
        return verified;
    }

    public void setVerified(Date verified) {
        this.verified = verified;
    }

    @Column(name = S_AUTHENTICATION_METHOD, nullable = false)
    @Enumerated(EnumType.STRING)
    public EAuthenticationMethod getAuthenticationMethod() {
        return authenticationMethod;
    }

    public void setAuthenticationMethod(EAuthenticationMethod authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }

    public void setAuthenticationMethod(String authenticationMethod) {
        this.authenticationMethod = (authenticationMethod != null) ? EAuthenticationMethod.valueOf(authenticationMethod) : null;
    }

    // --------------------------- JSON ------------------------------------------

    public EntryPoint(final JsonObject jsonObject) {
        super(jsonObject);
        this.uid = JsonUtils.getString(jsonObject, S_UID);
        this.password = JsonUtils.getString(jsonObject, S_PASSWORD);
        this.verified = JsonUtils.getDate(jsonObject, S_VERIFIED);
        setAuthenticationMethod(JsonUtils.getString(jsonObject, S_AUTHENTICATION_METHOD));
    }

    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_UID, uid);
        JsonUtils.set(jsonObject, S_PASSWORD, password);
        JsonUtils.set(jsonObject, S_VERIFIED, verified);
        JsonUtils.set(jsonObject, S_AUTHENTICATION_METHOD, authenticationMethod);
        return jsonObject;
    }
}
