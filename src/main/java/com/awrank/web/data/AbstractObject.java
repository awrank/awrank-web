package com.awrank.web.data;

import com.awrank.web.data.constant.AbstractObjectConst;
import com.awrank.web.data.constant.EObjectType;
import com.awrank.web.utils.JsonUtils;
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
    private EObjectType objectType;
    /**
     * идентификатор
     */
    private Long id;
    /**
     * дата создания
     */
    private Date created;
    /**
     * дата изменения
     */
    private Date updated;
    /**
     * дата деактивации
     */
    private Date ended;

    protected AbstractObject() {
    }

    protected AbstractObject(Long id) {
        this.id = id;
    }

    @Column(name = S_OBJECT_TYPE, nullable = false)
    @Enumerated(EnumType.STRING)
    public final EObjectType getObjectType() {
        return objectType;
    }

    public final void setObjectType(EObjectType objectType) {
        this.objectType = objectType;
    }

    public final void setObjectType(String objectType) {
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

    @Column(name = S_CREATED, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = S_UPDATED, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Column(name = S_ENDED, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEnded() {
        return ended;
    }

    public void setEnded(Date ended) {
        this.ended = ended;
    }

    // ------------------------------- JSON ---------------------------------------

    public AbstractObject(final JsonObject jsonObject) {
        setObjectType(JsonUtils.getString(jsonObject, S_OBJECT_TYPE));
        this.id = JsonUtils.getLong(jsonObject, S_ID);
        this.created = JsonUtils.getDate(jsonObject, S_CREATED);
        this.updated = JsonUtils.getDate(jsonObject, S_UPDATED);
        this.ended = JsonUtils.getDate(jsonObject, S_ENDED);
    }

    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        JsonUtils.set(jsonObject, S_OBJECT_TYPE, objectType.name());
        JsonUtils.set(jsonObject, S_ID, id);
        JsonUtils.set(jsonObject, S_CREATED, created);
        JsonUtils.set(jsonObject, S_UPDATED, updated);
        JsonUtils.set(jsonObject, S_ENDED, ended);
        return jsonObject;
    }

// ------------------------------ String --------------------------------------

    @Override
    public String toString() {
        return toJsonObject().toString();
    }
}
