package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.EObjectType;
import com.awrank.web.model.domain.constant.EntryHistoryConst;
import com.awrank.web.model.utils.json.JsonUtils;
import org.codehaus.jackson.node.ObjectNode;

import javax.persistence.*;

/**
 * история посещений
 */
@Entity
@Table(name = EntryHistoryConst.TABLE_NAME)
public class EntryHistory extends AbstractObject implements EntryHistoryConst {
    /**
     * точка входа
     */
    private EntryPoint entryPoint;
    /**
     * ip адресс
     */
    private String ipAddress;
    /**
     * вход выполнен успешно
     */
    private Boolean success;
    /**
     * идентификатор сессии
     */
    private String sessionId;
    /**
     * количество запросов за сессию
     */
    private Integer countRequest;

    {
        objectType = EObjectType.ENTRY_HISTORY;
    }

    public EntryHistory() {
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = S_ENTRY_POINT, nullable = false)
    public EntryPoint getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(EntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    @Column(name = S_IP_ADDRESS, nullable = false)
    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    @Column(name = S_SUCCESS, nullable = false)
    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Column(name = S_SESSION_ID, nullable = true)
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Column(name = S_COUNT_REQUEST, nullable = false)
    public Integer getCountRequest() {
        return countRequest;
    }

    public void setCountRequest(Integer countRequest) {
        this.countRequest = countRequest;
    }

    // --------------------------- JSON ------------------------------------------

    public EntryHistory(final ObjectNode jsonObject) {
        super(jsonObject);
        // entryPoint
        this.ipAddress = JsonUtils.getString(jsonObject, S_IP_ADDRESS);
        this.success = JsonUtils.getBoolean(jsonObject, S_SUCCESS);
        this.sessionId = JsonUtils.getString(jsonObject, S_SESSION_ID);
        this.countRequest = JsonUtils.getInteger(jsonObject, S_COUNT_REQUEST);
    }

    @Override
    public ObjectNode toJsonObject() {
        final ObjectNode jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_ENTRY_POINT, entryPoint);
        JsonUtils.set(jsonObject, S_IP_ADDRESS, ipAddress);
        JsonUtils.set(jsonObject, S_SUCCESS, success);
        JsonUtils.set(jsonObject, S_SESSION_ID, sessionId);
        JsonUtils.set(jsonObject, S_COUNT_REQUEST, countRequest);
        return jsonObject;
    }

}
