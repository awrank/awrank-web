package com.awrank.web.model.domain;

/**
 * The <code>DiaryEvent</code> is represented diary event information.
 *
 * @author Eugene Solomka
 */
public enum DiaryEvent {
    /**
     * When user changes his/her email address
     */
    CHANGE_EMAIL,

    /**
     * When user enter (first time) or register via social network (Google / Facebook)
     */
    REGISTER_SOCIAL_ENTRY_POINT,

    /**
     * When user enters incorrect password
     */
    INCORRECT_PASSWORD,

    /**
     * When user changes password
     */
    CHANGE_PASSWORD,

    /**
     * When user orders some product
     */
    SUBSCRIPTION_UPDATE,

    /**
     * When user starts payment process
     */
    START_PAY,

    /**
     * When user successfully finished payment
     */
    SUCCESSFULLY_PAID,

    /**
     * When error during payment process happened
     */
    ERROR_PAID,

    /**
     * When user login and there is another session that still active
     */
    USER_ALREADY_SIGNED_IN,

    /**
     * When user is blocked
     */
    BLOCKED,
    
    /**
     * User unblocked
     */
    UNBLOCKED

}
