package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.ERole;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;

/**
 * права пользователя
 */
@Entity
@Table(name = "user_roles")
public class UserRole extends AbstractPersistable<Long>{

  
    private ERole role;

  
    public UserRole() {
    }

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }


   
}
