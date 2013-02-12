package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.ExtendedAbstractAuditable;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The <b>ProductProfile</b> class defines product characteristic for certain moment of time.
 */
@Entity
@Table(name = "product_profiles")
public class ProductProfile extends ExtendedAbstractAuditable<Long> {
    /**
     * Price of the product in certain moment.
     */
    @Column(name = "price", columnDefinition = "DECIMAL(8,2)", nullable = false)
    private BigDecimal price;

    /**
     * Discount of the product in certain moment.
     */
    @Column(name = "discount", columnDefinition = "DECIMAL(8,2)", nullable = true)
    private BigDecimal discount;

    /**
     * Start date of profile. Together with endDate defines active period for product profile.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "started_at", nullable = false)
    private Date startedDate;

    /**
     * End date of profile.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "ended_at", nullable = false)
    private Date endedDate;

    /**
     * Product that profile belong to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;


    public ProductProfile() {
    }


    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public DateTime getStartedDate() {
        return (null == startedDate) ? null : new DateTime(startedDate);
    }

    public void setStartedDate(DateTime startedDate) {
        this.startedDate = (null == startedDate) ? null : startedDate.toDate();
    }

    public DateTime getEndedDate() {
        return (null == endedDate) ? null : new DateTime(endedDate);
    }

    public void setEndedDate(DateTime endedDate) {
        this.endedDate = (null == startedDate) ? null : endedDate.toDate();
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
