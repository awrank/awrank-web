package com.awrank.web.model.domain;

import org.joda.time.DateTime;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

/**
 * Entry history of user
 */
@Entity
@Table(name = "entry_history")
public class EntryHistory extends AbstractPersistable<Long> {
    /**
     * Remote IP address where user was signed in.
     */
    @Column(name = "ip_addres", nullable = false, length = 64)
    private String ipAddress;

    /**
     * Entered successfully?
     */
    @Column(name = "success", nullable = false, length = 64)
    private boolean success;

    /**
     * Session Identifier
     */
    @Column(name = "session_id", nullable = false)
    private String sessionId;

    /**
     * Request spend for session.
     */
    @Column(name = "spend_requests")
    private Integer spendRequests;

    /**
     * Date when user has signed in.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "signed_in_at", nullable = false)
    private Date signinDate;

    /**
     * Date when user has signed out.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "signed_out_at", nullable = true)
    private Date signoutDate;

    /**
     * Entry point that entry history belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entry_point_id", nullable = false)
    private EntryPoint entryPoint;

    /**
     * User that entry history belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public EntryHistory() {
    }


    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getSpendRequests() {
        return spendRequests;
    }

    public void setSpendRequests(Integer spendRequests) {
        this.spendRequests = spendRequests;
    }

    public DateTime getSigninDate() {
        return (null == signinDate) ? null : new DateTime(signinDate);
    }

    public void setSigninDate(DateTime signinDate) {
        this.signinDate = (null == signinDate) ? null : signinDate.toDate();
    }

    public DateTime getSignoutDate() {
        return (null == signoutDate) ? null : new DateTime(signoutDate);
    }

    public void setSignoutDate(DateTime signoutDate) {
        this.signoutDate = (null == signoutDate) ? null : signoutDate.toDate();
    }

    public EntryPoint getEntryPoint() {
        return entryPoint;
    }

    public void setEntryPoint(EntryPoint entryPoint) {
        this.entryPoint = entryPoint;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
