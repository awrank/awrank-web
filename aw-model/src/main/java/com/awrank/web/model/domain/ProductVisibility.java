package com.awrank.web.model.domain;

/**
 * The <code>ProductVisibility</code> represents enum for product visibility information.
 */
public enum ProductVisibility {
    /**
     * When product shown only to restricted users (e.g. admin).
     */
    RESTRICTED,

    /**
     * When product shown only to registered user.
     */
    REGISTERED,

    /**
     * When product shown to all users
     */
    ALL
}
