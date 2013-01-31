package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.EObjectType;
import com.awrank.web.model.domain.constant.ERole;
import com.awrank.web.model.domain.constant.UserRoleConst;
import com.awrank.web.model.utils.json.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.*;

/**
 * права пользователя
 */
@Entity
@Table(name = UserRoleConst.TABLE_NAME)
public class UserRole extends AbstractUserItem implements UserRoleConst {

    /**
     * доступ
     */
    private ERole role;

    {
        objectType = EObjectType.USER_ROLE;
    }

    public UserRole() {
    }

    @Column(name = S_ROLE, nullable = false)
    @Enumerated(EnumType.STRING)
    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }


    // --------------------------- JSON ------------------------------------------

    public UserRole(final JsonObject jsonObject) {
        super(jsonObject);
        this.role = JsonUtils.getEnum(jsonObject, S_ROLE, ERole.class);
    }

    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_ROLE, role);
        return jsonObject;
    }
}
