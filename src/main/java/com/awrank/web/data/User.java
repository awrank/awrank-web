package com.awrank.web.data;

import com.awrank.web.data.constant.ELanguage;
import com.awrank.web.data.constant.UserConst;
import com.awrank.web.utils.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.*;
import java.util.Date;

/**
 * пользователь
 */
@Entity
@Table(name = UserConst.TABLE_NAME)
public class User extends AbstractObject implements UserConst {
    /**
     * уникальный код, который будет использоваться для запросов к API
     */
    private String apiKey;
    /**
     * пользователь, пригласивший в систему
     */
    private User refUser;
    /**
     * email пользователя
     */
    private String email;
    /**
     * скайп
     */
    private String skype;
    /**
     * имя
     */
    private String firstName;
    /**
     * фамилия
     */
    private String lastName;
    /**
     * день рождения
     */
    private Date birthday;
    /**
     * ответ на секретный вопрос
     */
    private String secretAnswer;
    /**
     * язык пользователя
     */
    private ELanguage language;
    /**
     * количество неудачных аутентификации
     */
    private Integer authorizationFailsCount;
    /**
     * последняя неудачная попытка
     */
    private Date authorizationFailsLast;
    /**
     * дата блокирокви
     */
    private Date banStarted;

    public User() {
    }

    @Column(name = S_API_KEY, nullable = false, unique = true)
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = S_REF_USER, nullable = true)
    public User getRefUser() {
        return refUser;
    }

    public void setRefUser(User refUser) {
        this.refUser = refUser;
    }

    @Column(name = S_EMAIL, nullable = false, unique = true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = S_SKYPE, nullable = true)
    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    @Column(name = S_FIRST_NAME, nullable = true)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = S_LAST_NAME, nullable = true)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = S_BIRTHDAY, nullable = true)
    @Temporal(TemporalType.DATE)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column(name = S_SECRET_ANSWER, nullable = true)
    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    @Column(name = S_LANGUAGE, nullable = false)
    @Enumerated(EnumType.STRING)
    public ELanguage getLanguage() {
        return language;
    }

    public void setLanguage(ELanguage language) {
        this.language = language;
    }

    public void setLanguage(String language) {
        this.language = (language != null) ? ELanguage.valueOf(language) : null;
    }

    @Column(name = S_AUTHORIZATION_FAILS_COUNT, nullable = false)
    public Integer getAuthorizationFailsCount() {
        return authorizationFailsCount;
    }

    public void setAuthorizationFailsCount(Integer authorizationFailsCount) {
        this.authorizationFailsCount = authorizationFailsCount;
    }

    @Column(name = S_AUTHORIZATION_FAILS_LAST, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getAuthorizationFailsLast() {
        return authorizationFailsLast;
    }

    public void setAuthorizationFailsLast(Date authorizationFailsLast) {
        this.authorizationFailsLast = authorizationFailsLast;
    }

    @Column(name = S_BAN_STARTED, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getBanStarted() {
        return banStarted;
    }

    public void setBanStarted(Date banStarted) {
        this.banStarted = banStarted;
    }

    // --------------------------- JSON ------------------------------------------

    public User(final JsonObject jsonObject) {
        super(jsonObject);
        this.apiKey = JsonUtils.getString(jsonObject, S_API_KEY);
        // refUser
        this.email = JsonUtils.getString(jsonObject, S_EMAIL);
        this.skype = JsonUtils.getString(jsonObject, S_SKYPE);
        this.firstName = JsonUtils.getString(jsonObject, S_FIRST_NAME);
        this.lastName = JsonUtils.getString(jsonObject, S_LAST_NAME);
        this.birthday = JsonUtils.getDate(jsonObject, S_BIRTHDAY);
        this.secretAnswer = JsonUtils.getString(jsonObject, S_SECRET_ANSWER);
        setLanguage(JsonUtils.getString(jsonObject, S_LANGUAGE));
        this.authorizationFailsCount = JsonUtils.getInteger(jsonObject, S_AUTHORIZATION_FAILS_COUNT);
        this.authorizationFailsLast = JsonUtils.getDate(jsonObject, S_AUTHORIZATION_FAILS_LAST);
        this.banStarted = JsonUtils.getDate(jsonObject, S_BAN_STARTED);
    }

    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_API_KEY, apiKey);
        JsonUtils.set(jsonObject, S_REF_USER, refUser);
        JsonUtils.set(jsonObject, S_EMAIL, email);
        JsonUtils.set(jsonObject, S_SKYPE, skype);
        JsonUtils.set(jsonObject, S_FIRST_NAME, firstName);
        JsonUtils.set(jsonObject, S_LAST_NAME, lastName);
        JsonUtils.set(jsonObject, S_BIRTHDAY, birthday);
        JsonUtils.set(jsonObject, S_SECRET_ANSWER, secretAnswer);
        JsonUtils.set(jsonObject, S_LANGUAGE, language);
        JsonUtils.set(jsonObject, S_AUTHORIZATION_FAILS_COUNT, authorizationFailsCount);
        JsonUtils.set(jsonObject, S_AUTHORIZATION_FAILS_LAST, authorizationFailsLast);
        JsonUtils.set(jsonObject, S_BAN_STARTED, banStarted);
        return jsonObject;
    }
}
