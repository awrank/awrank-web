package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.AbstractObjectConst;
import com.awrank.web.model.domain.constant.EObjectType;
import com.awrank.web.model.utils.json.JsonUtils;
import com.awrank.web.model.utils.user.CurrentUserUtils;
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
     * versioning property for provide optimistic locking
     */
    private Long version;
    /**
     * дата создания
     */
    private Date createdDate;
    /**
     * user who created this entity
     */
    private User createdBy;
    /**
     * дата изменения
     */
    private Date lastModifiedDate;
    /**
     * user who modified the entity lastly
     */
    private User lastModifiedBy;
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

    @Id
    @Column(name = S_ID, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    //@Override
    public final Long getId() {
        return id;
    }

    public final void setId(Long id) {
        this.id = id;
    }

    //    @Override
    @Transient
    public boolean isNew() {
        return createdDate == null;
    }

    @Column(name = S_VERSION, nullable = false)
    @Version
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Column(name = S_CREATED_DATE, nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    //@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    //@Type(type="org.jadira.usertype.dateandtime.jsr310.PersistentLocalDateTime")
    //@Override
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = S_CREATED_BY, nullable = true, updatable = true)
    //@Override
    public User getCreatedBy() {
        return createdBy;
    }

    //@Override
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = S_LAST_MODIFIED_DATE, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    //@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    //@Type(type="org.jadira.usertype.dateandtime.jsr310.PersistentLocalDateTime")
    //@Override
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = S_LAST_MODIFIED_BY, nullable = true)
    //@Override
    public User getLastModifiedBy() {
        return lastModifiedBy;
    }

    //@Override
    public void setLastModifiedBy(User lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    @Column(name = S_ENDED_DATE, nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    //@Type(type="org.joda.time.contrib.hibernate.PersistentDateTime")
    //@Type(type="org.jadira.usertype.dateandtime.jsr310.PersistentLocalDateTime")
    public Date getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(Date endedDate) {
        this.endedDate = endedDate;
    }


    @PrePersist
    public void prePersist() throws Exception {
        createdDate = new Date();
        if (createdBy == null) {
            createdBy = CurrentUserUtils.getCurrentUser();
        }
        lastModifiedDate = createdDate;
        lastModifiedBy = createdBy;
    }

    @PreUpdate
    public void preUpdate() throws Exception {
        lastModifiedDate = new Date();
        if (lastModifiedBy == null) {
            lastModifiedBy = CurrentUserUtils.getCurrentUser();
        }
    }

    // ------------------------------- JSON ---------------------------------------

    public AbstractObject(final JsonObject jsonObject) {
        this.objectType = JsonUtils.getEnum(jsonObject, S_OBJECT_TYPE, EObjectType.class);
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
