package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.AbstractUserItemConst;
import com.awrank.web.model.utils.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

/**
 *
 */
@MappedSuperclass
public abstract class AbstractUserItem extends AbstractObject implements AbstractUserItemConst {
    /**
     * пользователь
     */
    private User user;

    protected AbstractUserItem() {
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = S_USER, nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // --------------------------- JSON ------------------------------------------

    public AbstractUserItem(final JsonObject jsonObject) {
        super(jsonObject);
        // user
    }

    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_USER, user);
        return jsonObject;
    }
}