package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.ExtendedAbstractAuditable;
import com.awrank.web.model.enums.Role;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

/**
 * права пользователя
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "user_roles")
public class UserRole extends ExtendedAbstractAuditable<Long>{// AbstractPersistable<Long>{

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

    /**
     * User that role belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
	
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
   
}
