package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.ExtendedAbstractAuditable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * Payout to referral users.
 *
 * @author Eugene Solomka
 */
@Entity
@Table(name = "payouts")
public class Payout extends ExtendedAbstractAuditable<Long> {
    /**
     * Amount payout to referral user.
     */
    @Column(name = "amount", columnDefinition = "DECIMAL(8,2)", nullable = false)
    private BigDecimal amount;


//    /**
//     * в днях, спустя какой период времени будет производиться оплата
//     */
//    private Integer gracePeriod;
}
