package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.ELanguage;
import com.awrank.web.model.domain.constant.EObjectType;
import com.awrank.web.model.domain.constant.ESecretQuestion;
import com.awrank.web.model.domain.constant.UserHistoryConst;
import com.awrank.web.model.utils.json.JsonUtils;
import org.codehaus.jackson.node.ObjectNode;

import javax.persistence.*;
import java.util.Date;

/**
 * история пользователя
 */
@Entity
@Table(name = UserHistoryConst.TABLE_NAME)
public class UserHistory extends AbstractObject implements UserHistoryConst {

    /**
     * дата добавления записи в историю
     */
    private Date createdHistory;

    /**
     * идентификатор пользователя
     */
    private Long userId;

    /**
     * уникальный код, который будет использоваться для запросов к API
     */
    private String apiKey;
    /**
     * идентификатор пользователя, пригласивший в систему
     */
    private Long refUserId;
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
     * code in dictionary for secret question
     * код текст из словаря для секретного вопроса
     */
    private ESecretQuestion secretQuestionDicCode;
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
    private Date authorizationFailsLastDate;
    /**
     * дата блокирокви
     */
    private Date banStartedDate;

    {
        objectType = EObjectType.USER_HISTORY;
    }

    public UserHistory() {
    }

    @Column(name = S_CREATED_HISTORY, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    //@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
//@Type(type="org.jadira.usertype.dateandtime.jsr310.PersistentLocalDateTime")
    public Date getCreatedHistory() {
        return createdHistory;
    }

    public void setCreatedHistory(Date createdHistory) {
        this.createdHistory = createdHistory;
    }

    @Column(name = S_USER_ID, nullable = true)
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Column(name = S_API_KEY, nullable = true)
    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    @Column(name = S_REF_USER_ID, nullable = true)
    public Long getRefUserId() {
        return refUserId;
    }

    public void setRefUserId(Long refUserId) {
        this.refUserId = refUserId;
    }

    @Column(name = S_EMAIL, nullable = true)
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
    //@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
//@Type(type="org.jadira.usertype.dateandtime.jsr310.PersistentLocalDateTime")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Column(name = S_SECRET_QUESTION_DIC_CODE, nullable = true)
    @Enumerated(EnumType.STRING)
    public ESecretQuestion getSecretQuestionDicCode() {
        return secretQuestionDicCode;
    }

    public void setSecretQuestionDicCode(ESecretQuestion secretQuestionDicCode) {
        this.secretQuestionDicCode = secretQuestionDicCode;
    }

    @Column(name = S_SECRET_ANSWER, nullable = true)
    public String getSecretAnswer() {
        return secretAnswer;
    }

    public void setSecretAnswer(String secretAnswer) {
        this.secretAnswer = secretAnswer;
    }

    @Column(name = S_LANGUAGE, nullable = true)
    @Enumerated(EnumType.STRING)
    public ELanguage getLanguage() {
        return language;
    }

    public void setLanguage(ELanguage language) {
        this.language = language;
    }

    @Column(name = S_AUTHORIZATION_FAILS_COUNT, nullable = true)
    public Integer getAuthorizationFailsCount() {
        return authorizationFailsCount;
    }

    public void setAuthorizationFailsCount(Integer authorizationFailsCount) {
        this.authorizationFailsCount = authorizationFailsCount;
    }

    @Column(name = S_AUTHORIZATION_FAILS_LAST_DATE, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    //@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
//@Type(type="org.jadira.usertype.dateandtime.jsr310.PersistentLocalDateTime")
    public Date getAuthorizationFailsLastDate() {
        return authorizationFailsLastDate;
    }

    public void setAuthorizationFailsLastDate(Date authorizationFailsLastDate) {
        this.authorizationFailsLastDate = authorizationFailsLastDate;
    }

    @Column(name = S_BAN_STARTED_DATE, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    //@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
//@Type(type="org.jadira.usertype.dateandtime.jsr310.PersistentLocalDateTime")
    public Date getBanStartedDate() {
        return banStartedDate;
    }

    public void setBanStartedDate(Date banStartedDate) {
        this.banStartedDate = banStartedDate;
    }

    // --------------------------- JSON ------------------------------------------

    public UserHistory(final ObjectNode jsonObject) {
        super(jsonObject);
        this.createdHistory = JsonUtils.getDate(jsonObject, S_CREATED_HISTORY);
        this.userId = JsonUtils.getLong(jsonObject, S_USER_ID);
        this.apiKey = JsonUtils.getString(jsonObject, S_API_KEY);
        this.refUserId = JsonUtils.getLong(jsonObject, S_REF_USER_ID);
        this.email = JsonUtils.getString(jsonObject, S_EMAIL);
        this.skype = JsonUtils.getString(jsonObject, S_SKYPE);
        this.firstName = JsonUtils.getString(jsonObject, S_FIRST_NAME);
        this.lastName = JsonUtils.getString(jsonObject, S_LAST_NAME);
        this.birthday = JsonUtils.getDate(jsonObject, S_BIRTHDAY);
        this.secretQuestionDicCode = JsonUtils.getEnum(jsonObject, S_SECRET_QUESTION_DIC_CODE, ESecretQuestion.class);
        this.secretAnswer = JsonUtils.getString(jsonObject, S_SECRET_ANSWER);
        this.language = JsonUtils.getEnum(jsonObject, S_LANGUAGE, ELanguage.class);
        this.authorizationFailsCount = JsonUtils.getInteger(jsonObject, S_AUTHORIZATION_FAILS_COUNT);
        this.authorizationFailsLastDate = JsonUtils.getDate(jsonObject, S_AUTHORIZATION_FAILS_LAST_DATE);
        this.banStartedDate = JsonUtils.getDate(jsonObject, S_BAN_STARTED_DATE);
    }

    @Override
    public ObjectNode toJsonObject() {
        final ObjectNode jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_CREATED_HISTORY, createdHistory);
        JsonUtils.set(jsonObject, S_USER_ID, userId);
        JsonUtils.set(jsonObject, S_API_KEY, apiKey);
        JsonUtils.set(jsonObject, S_REF_USER_ID, refUserId);
        JsonUtils.set(jsonObject, S_EMAIL, email);
        JsonUtils.set(jsonObject, S_SKYPE, skype);
        JsonUtils.set(jsonObject, S_FIRST_NAME, firstName);
        JsonUtils.set(jsonObject, S_LAST_NAME, lastName);
        JsonUtils.set(jsonObject, S_BIRTHDAY, birthday);
        JsonUtils.set(jsonObject, S_SECRET_QUESTION_DIC_CODE, secretQuestionDicCode);
        JsonUtils.set(jsonObject, S_SECRET_ANSWER, secretAnswer);
        JsonUtils.set(jsonObject, S_LANGUAGE, language);
        JsonUtils.set(jsonObject, S_AUTHORIZATION_FAILS_COUNT, authorizationFailsCount);
        JsonUtils.set(jsonObject, S_AUTHORIZATION_FAILS_LAST_DATE, authorizationFailsLastDate);
        JsonUtils.set(jsonObject, S_BAN_STARTED_DATE, banStartedDate);
        return jsonObject;
    }
}
