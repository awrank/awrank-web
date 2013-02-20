package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.DatedAbstractAuditable;
import com.awrank.web.model.enums.Role;

import javax.persistence.*;

/**
 * The class describes domain entity which reflects user roles.
 *
 * @author Alex Polyakov
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "user_roles")
public class UserRole extends DatedAbstractAuditable<Long> {

    /**
     * A user that owns the role.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private Role role;

    public UserRole() {
    }

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
