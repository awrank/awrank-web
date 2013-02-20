package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.AbstractUserRelatedEntityAuditable;

import javax.persistence.*;
import java.util.Date;

/**
 * Verification of email entered by user during registration
 *
 * @author Alex Polyakov
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "user_email_activation")
public class UserEmailActivation extends AbstractUserRelatedEntityAuditable<Long> {

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    @Column(name = "new_email", nullable = true)
    private String newEmail;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "email_verified_at", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date emailVerifiedDate;

    /**
     * Constructor
     */
    public UserEmailActivation() {
    }

    /* getters & setters */

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getEmailVerifiedDate() {
        return emailVerifiedDate;
    }

    public void setEmailVerifiedDate(Date emailVerifiedDate) {
        this.emailVerifiedDate = emailVerifiedDate;
    }
}
