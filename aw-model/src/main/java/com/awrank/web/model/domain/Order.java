package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.AbstractUserRelatedEntityAuditable;

import javax.persistence.*;

/**
 * The <b>Order</b> class represents an order.
 *
 * @author Eugene Solomka
 * @author Andrew Stoyaltsev
 */
@Entity
@Table(name = "orders")
public class Order extends AbstractUserRelatedEntityAuditable<Long> {

    /**
     * ProductProfile under which created Order.
     */
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_profile_id", nullable = false, updatable = false)
    private ProductProfile productProfile;

    /**
     * Current order status.
     */
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.UNPAID;

    public Order() {
    }

    public ProductProfile getProductProfile() {
        return productProfile;
    }

    public void setProductProfile(ProductProfile productProfile) {
        this.productProfile = productProfile;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /**
     * Returns <code>true</code> if order is not paid.
     */
    public boolean isUnpaid() {
        return OrderStatus.UNPAID.equals(status);
    }

    /**
     * Returns <code>true</code> if order is paid.
     */
    public boolean isPaid() {
        return OrderStatus.PAID.equals(status);
    }

    /**
     * Returns <code>true</code> if order is deleted.
     */
    public boolean isDeleted() {
        return OrderStatus.DELETED.equals(status);
    }

}