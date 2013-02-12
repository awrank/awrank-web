package com.awrank.web.model.domain;

import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

/**
 * The <b>EntryPoint</b> class represents an entry point.
 */
@Entity
@Table(name = "entry_point")
public class EntryPoint extends AbstractPersistable<Long> {
    /**
     * User identifier for sign in (could be email or identifier in social network)
     */
    @Column(name = "uid", nullable = false)
    private String uid;

    /**
     * Password (required only for email sign in)
     * encoded with SHA-2 + solt
     */
    @Column(name = "password")
    private String password;

    /**
     * Date when entry point was verified
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "verified_at")
    private Date verifiedDate;

    /**
     * Type of entry point (email, facebook, google)
     */
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntryPointType type;

    /**
     * User that entry history belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public EntryPoint() {
    }


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DateTime getVerifiedDate() {
        return (null == verifiedDate) ? null : new DateTime(verifiedDate);
    }

    public void setVerifiedDate(DateTime verifiedDate) {
        this.verifiedDate = (null == verifiedDate) ? null : verifiedDate.toDate();
    }

    public EntryPointType getType() {
        return type;
    }

    public void setType(EntryPointType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
