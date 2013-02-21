package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.AbstractUserRelatedEntityAuditable;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

/**
 * Diary entry about user's information changes
 *
 * @author Eugene Solomka
 */
@Entity
@Table(name = "diary")
public class Diary extends AbstractUserRelatedEntityAuditable<Long> {

    /**
     * Entry history that diary entry belongs to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entry_history_id", nullable = false)
    private EntryHistory entryHistory;

    /**
     * Previous value.
     */
    @Column(name = "old_val", length = 255)
    private String oldValue;

    /**
     * New value.
     */
    @Column(name = "new_val", length = 255)
    private String newValue;

    /**
     * Event.
     */
    @Column(name = "event", nullable = false)
    @Enumerated(EnumType.STRING)
    private DiaryEvent event;

    /**
     * Date when event happened.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime date;

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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public EntryHistory getEntryHistory() {
        return entryHistory;
    }

    public void setEntryHistory(EntryHistory entryHistory) {
        this.entryHistory = entryHistory;
    }

}
