package com.awrank.web.model.domain.support;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * The class extends {@link ExtendedAbstractAuditable} with date properties, which should be mapped to corresponding
 * columns in tables.
 * The initial version of this class (1.0) contains only {@code ended_at} field. But it could be extended with other
 * date field yuo might need.
 *
 * @version 1.0
 * @author Andrew Stoyaltsev
 */
public class DatedAbstractAuditable<ID extends Serializable> extends ExtendedAbstractAuditable<ID> {

    /**
     * A date when a table record was finished.
     * It can be considered as simulation of record removing, or invalidating.
     */
    @Column(name = "ended_at", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endedDate;

    /* getters & setters */

    public Date getEndedDate() {
        return endedDate;
    }

    public void setEndedDate(Date endedDate) {
        this.endedDate = endedDate;
    }
}
