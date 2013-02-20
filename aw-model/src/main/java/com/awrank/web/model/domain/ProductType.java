package com.awrank.web.model.domain;

/**
 * The <code>ProductType</code> represents enum for product type information.
 *
 * @author Eugene Solomka
 */
public enum ProductType {
    /**
     * When product does not define expiration period for requests.
     */
    UNLIMITED,

    /**
     * When product defines expiration period defined in months.
     */
    MONTHLY,

    /**
     * When product defines expiration period defined in days.
     */
    DAILY
}
