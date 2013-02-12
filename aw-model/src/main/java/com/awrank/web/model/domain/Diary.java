package com.awrank.web.model.domain;

import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

/**
 * Diary entry about user's information changes
 */
@Entity
@Table(name = "diary")
public class Diary extends AbstractPersistable<Long> {
    /**
     * Previous value
     */
    @Column(name = "old_val", length = 255)
    private String oldValue;

    /**
     * New value
     */
    @Column(name = "new_val", length = 255)
    private String newValue;

    /**
     * Event
     */
    @Column(name = "event", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiaryEvent event;

    /**
     * Date when event happened
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date", nullable = false)
    private Date date;

    /**
     * Entry history that diary entry belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entry_history_id", nullable = false)
    private EntryHistory entryHistory;

    /**
     * User that order belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Diary() {
    }


    public String getOldValue() {
        return oldValue;
    }

    public void setOldValue(String oldValue) {
        this.oldValue = oldValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }

    public DiaryEvent getEvent() {
        return event;
    }

    public void setEvent(DiaryEvent event) {
        this.event = event;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public EntryHistory getEntryHistory() {
        return entryHistory;
    }

    public void setEntryHistory(EntryHistory entryHistory) {
        this.entryHistory = entryHistory;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
