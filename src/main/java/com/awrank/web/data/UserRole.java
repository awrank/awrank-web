package com.awrank.web.data;

import com.awrank.web.data.constant.ERole;
import com.awrank.web.data.constant.UserRoleConst;
import com.awrank.web.utils.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.*;

/**
 * права пользователя
 */
@Entity
@Table(name = UserRoleConst.TABLE_NAME)
public class UserRole extends AbstractObject implements UserRoleConst {

    /**
     * пользователь
     */
    private User user;
    /**
     * доступ
     */
    private ERole role;

    public UserRole() {
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = S_USER, nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = S_ROLE, nullable = false)
    @Enumerated(EnumType.STRING)
    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public void setRole(String role) {
        this.role = (role != null) ? ERole.valueOf(role) : null;
    }

    // --------------------------- JSON ------------------------------------------

    public UserRole(final JsonObject jsonObject) {
        super(jsonObject);
        // user
        setRole(JsonUtils.getString(jsonObject, S_ROLE));
    }

    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_USER, user);
        JsonUtils.set(jsonObject, S_ROLE, role);
        return jsonObject;
    }
}
