package com.awrank.web.model.domain;

/**
 * The <code>DiaryEvent</code> is represented diary event information.
 */
public enum DiaryEvent {
    /**
     * When user changes password
     */
    CHANGE_PASSWORD,

    /**
     * When user starts payment process
     */
    START_PAY,

    /**
     * When user successfully finished payment
     */
    SUCCESFULLY_PAID,

    /**
     * When error during payment process happened
     */
    ERROR_PAID

}
