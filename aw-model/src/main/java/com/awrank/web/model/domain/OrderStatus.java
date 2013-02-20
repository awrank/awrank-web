package com.awrank.web.model.domain;

/**
 * The <code>OrderStatus</code> represents enum for order status information.
 *
 * @author Eugene Solomka
 */
public enum OrderStatus {
    /**
     * Describes whether the order is <b>unpaid</b>.
     */
    UNPAID,

    /**
     * Describes whether the order is <b>paid</b>.
     */
    PAID,

    /**
     * Describes whether the order is <b>deleted</b>.
     */
    DELETED
}
