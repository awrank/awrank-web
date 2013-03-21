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
     * password manual change from profile
     */
    USER_PASSWORD_CHANGE,
    
    /**
     * user forgot password, token for new password can be entered from form
     */
    USER_FORGOT_PASSWORD

}
