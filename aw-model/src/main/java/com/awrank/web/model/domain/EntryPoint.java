package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.AbstractUserRelatedEntityAuditable;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.util.Date;

/**
 * The <b>EntryPoint</b> class represents an entry point.
 *
 * @author Alex Polyakov
 * @author Eugene Solomka
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "entry_point")
public class EntryPoint extends AbstractUserRelatedEntityAuditable<Long> {
    /**
     * User identifier for sign in (could be email or identifier in social network)
     */
    @Column(name = "uid", nullable = false)
    private String uid;

    /**
     * Password (required only for email sign in)
     * encoded with SHA-2 + salt
     */
    @Column(name = "password")
    private String password;

    /**
     * Date when entry point was verified
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "verified_at")
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime verifiedDate;

    /**
     * Type of entry point (email, facebook, google)
     */
    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EntryPointType type;


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

    public LocalDateTime getVerifiedDate() {
        return (null == verifiedDate) ? null : new LocalDateTime(verifiedDate);
    }

    public void setVerifiedDate(LocalDateTime verifiedDate) {
        this.verifiedDate = (null == verifiedDate) ? null : verifiedDate;
    }

    public EntryPointType getType() {
        return type;
    }

    public void setType(EntryPointType type) {
        this.type = type;
    }

}
