package com.awrank.web.model.enums;

/**
 * The enumeration contains secret questions which might be used during user credentials restoring process.
 *
 * @author Alex Polyakov
 */
public enum SecretQuestion {

    /**
     * Secret question about user's favourite number.
     */
    SECRET_QUESTION_FAVORITE_NUMBER(Message.SECRET_QUESTION_FAVORITE_NUMBER),

    /**
     * Secret question about something other.
     */
    SECRET_QUESTION_OTHER(Message.SECRET_QUESTION_OTHER);

    private Message value;

    private SecretQuestion(Message message) {
        this.value = message;
    }

}
