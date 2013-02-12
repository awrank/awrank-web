package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.ExtendedAbstractAuditable;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.*;

/**
 * The <b>Order</b> class represents an order.
 */
@Entity
@Table(name = "orders")
public class Order extends ExtendedAbstractAuditable<Long> {
    /**
     * Current order status.
     */
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.UNPAID;

    /**
     * User that order belongs to.
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    public Order() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}