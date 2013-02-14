package com.awrank.web.model.domain;

import javax.persistence.*;
import java.util.Date;

import com.awrank.web.model.domain.support.AbstractUserRelatedEntityAuditable;

/**
 * order
 */
@Entity
@Table(name = "user_order")
public class UserOrder  extends AbstractUserRelatedEntityAuditable<Long> {

    /**
     * tariff rettings
     */
	//TODO: replace with product
    //private TariffSettings tariffSettings;
    /**
     * order was payed for and active
     */
    private Boolean complete;
    /**
     * TODO
     * ?
     */
    private User refUser;
    /**
     * in days, after what date user can't request for refund
     */
    private Integer gracePeriod;
    /**
     * date of payment
     */
    private Date payedDate;

       public UserOrder() {
    }

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = S_TARIFF_SETTINGS, nullable = false)
//    public TariffSettings getTariffSettings() {
//        return tariffSettings;
//    }
//
//    public void setTariffSettings(TariffSettings tariffSettings) {
//        this.tariffSettings = tariffSettings;
//    }

    @Column(name = "complete", nullable = false)
    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "reference_user", nullable = true)
    public User getRefUser() {
        return refUser;
    }

    public void setRefUser(User refUser) {
        this.refUser = refUser;
    }

    @Column(name = "grace_period", nullable = false)
    public Integer getGracePeriod() {
        return gracePeriod;
    }

    public void setGracePeriod(Integer gracePeriod) {
        this.gracePeriod = gracePeriod;
    }

    @Column(name = "date_of_payment", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    public Date getPayedDate() {
        return payedDate;
    }

    public void setPayedDate(Date payedDate) {
        this.payedDate = payedDate;
    }

}
