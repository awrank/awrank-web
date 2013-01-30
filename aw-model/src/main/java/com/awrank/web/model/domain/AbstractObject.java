package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.AbstractObjectConst;
import com.awrank.web.model.domain.constant.EObjectType;
import com.awrank.web.model.utils.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 */
@MappedSuperclass
public abstract class AbstractObject implements AbstractObjectConst, Serializable {
    /**
     * тип объекта
     */
    protected EObjectType objectType;
    /**
     * идентификатор
     */
    private Long id;
    /**
     * дата создания
     */
    private Date createdDate;
    /**
     * дата изменения
     */
    private Date lastModifiedDate;
    /**
     * дата деактивации
     */
    private Date endedDate;

    protected AbstractObject() {
    }

    protected AbstractObject(Long id) {
        this.id = id;
    }

    @Column(name = S_OBJECT_TYPE, nullable = false)
    @Enumerated(EnumType.STRING)
    public EObjectType getObjectType() {
        return objectType;
    }

    private void setObjectType(EObjectType objectType) {
        this.objectType = objectType;
    }

    private void setObjectType(String objectType) {
        this.objectType = (objectType != null) ? EObjectType.valueOf(objectType) : null;
    }

    @Id
    @Column(name = S_ID, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public final Long getId() {
        return id;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    @Column(name = S_CREATED_DATE, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = S_LAST_MODIFIED_DATE, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @Column(name = S_ENDED_DATE, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(Date endedDate) {
        this.endedDate = endedDate;
    }

    @PrePersist
    public void prePersist() throws Exception {
        createdDate = new Date();
        lastModifiedDate = createdDate;
    }

    @PreUpdate
    public void preUpdate() throws Exception {
        lastModifiedDate = new Date();
    }

    // ------------------------------- JSON ---------------------------------------

    public AbstractObject(final JsonObject jsonObject) {
        setObjectType(JsonUtils.getString(jsonObject, S_OBJECT_TYPE));
        this.id = JsonUtils.getLong(jsonObject, S_ID);
        this.createdDate = JsonUtils.getDate(jsonObject, S_CREATED_DATE);
        this.lastModifiedDate = JsonUtils.getDate(jsonObject, S_LAST_MODIFIED_DATE);
        this.endedDate = JsonUtils.getDate(jsonObject, S_ENDED_DATE);
    }

    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        JsonUtils.set(jsonObject, S_OBJECT_TYPE, objectType.name());
        JsonUtils.set(jsonObject, S_ID, id);
        JsonUtils.set(jsonObject, S_CREATED_DATE, createdDate);
        JsonUtils.set(jsonObject, S_LAST_MODIFIED_DATE, lastModifiedDate);
        JsonUtils.set(jsonObject, S_ENDED_DATE, endedDate);
        return jsonObject;
    }

// ------------------------------ String --------------------------------------

    @Override
    public String toString() {
        return toJsonObject().toString();
    }
}
