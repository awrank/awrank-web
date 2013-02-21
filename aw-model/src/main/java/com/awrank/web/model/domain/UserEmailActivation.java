package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.AbstractUserRelatedEntityAuditable;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

/**
 * Verification of email entered by user during registration.
 *
 * @author Alex Polyakov
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "user_email_activation")
public class UserEmailActivation extends AbstractUserRelatedEntityAuditable<Long> {

    /**
     * Generated code which is included into verification link (e.g. some hash value)
     */
    @Column(name = "code", nullable = false)
    private String code;

    /**
     * IP address from which a user came into the system via verification URL.
     */
    @Column(name = "ip_address", nullable = false)
    private String ipAddress;

    /**
     * New email.
     */
    @Column(name = "new_email", nullable = true)
    private String newEmail;

    /**
     * User email.
     */
    @Column(name = "email", nullable = false)
    private String email;

    /**
     * A date when new email verification was passed.
     */
    @Column(name = "email_verified_at", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime emailVerifiedDate;
    
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

    public LocalDateTime getEmailVerifiedDate() {
        return emailVerifiedDate;
    }

    public void setEmailVerifiedDate(LocalDateTime emailVerifiedDate) {
        this.emailVerifiedDate = emailVerifiedDate;
    }
}
