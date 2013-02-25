package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.DatedAbstractAuditable;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * The <b>ProductProfile</b> class defines product characteristic for certain moment of time.
 *
 * @author Eugene Solomka
 */
@Entity
@Table(name = "product_profiles")
public class ProductProfile extends DatedAbstractAuditable<Long> {
    /**
     * Product that profile belong to.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false, updatable = false)
    private Product product;

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
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    @Column(name = "started_at", nullable = false)
    private LocalDateTime startedDate;

    /**
     *
     */
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

    public LocalDateTime getStartedDate() {
        return startedDate;
    }

    public void setStartedDate(LocalDateTime startedDate) {
        this.startedDate = startedDate;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
