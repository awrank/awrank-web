package com.awrank.web.model.domain;

import com.awrank.web.model.enums.Role;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

/**
 * права пользователя
 */
@Entity
@Table(name = "user_roles")
public class UserRole extends AbstractPersistable<Long>{

  
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


   
}
