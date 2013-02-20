package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.DatedAbstractAuditable;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.Date;

/**
 * The {@code EntryHistory} class describes one record in history table related to specified user.
 *
 * @author Alex Polyakov
 * @author Eugene Solomka
 * @author Andrew Stoyaltsev
 */
@Entity
@Table(name = "entry_history")
public class EntryHistory extends DatedAbstractAuditable<Long> {

    /**
     * User that entry history belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * Entry point that entry history belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entry_point_id", nullable = false)
    private EntryPoint entryPoint;

    /**
     * Remote IP address where user was signed in.
     */
    @Column(name = "ip_address", nullable = false, length = 64)
    private String ipAddress;

    /**
     * Entered successfully?
     */
    @Column(name = "success", nullable = false)
    private boolean success;

    /**
     * Session identifier
     */
    @Column(name = "session_id", nullable = false)
    private String sessionId;

    /**
     * Request spent for session.
     */
    @Column(name = "spent_requests")
    private Integer spentRequests;

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

    public Integer getSpentRequests() {
        return spentRequests;
    }

    public void setSpentRequests(Integer spentRequests) {
        this.spentRequests = spentRequests;
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
