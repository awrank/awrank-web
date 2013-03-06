package com.awrank.web.model.enums;

/**
 * The enumeration describes change token types.
 *
 * @author Olga Korokhina
 */
public enum StateChangeTokenType {
    /**
     * email verification.
     */
    USER_EMAIL_VERIFICATION,
    
    /**
     * email change- new email verification.
     */
    USER_EMAIL_CHANGE,

    /**
     * password change
     */
    USER_PASSWORD_CHANGE,

}
