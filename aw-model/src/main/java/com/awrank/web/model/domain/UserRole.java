package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.AbstractUserRelatedEntityAuditable;
import com.awrank.web.model.enums.Role;

import javax.persistence.*;

/**
 * The class describes domain entity which reflects user roles.
 *
 * @author Alex Polyakov
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "user_roles")
public class UserRole extends AbstractUserRelatedEntityAuditable<Long> {

    /**
     * User role, e.g. ROLE_USER or ROLE_ADMIN.
     */
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public UserRole() {
    }
   
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}
