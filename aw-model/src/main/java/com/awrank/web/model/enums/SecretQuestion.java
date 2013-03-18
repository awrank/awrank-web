package com.awrank.web.model.enums;

/**
 * The enumeration contains secret questions which might be used during user credentials restoring process.
 *
 * @author Alex Polyakov
 */
public enum SecretQuestion {

    /**
     * Secret question about user's first pet.
     */
	SECRET_QUESTION_FIRST_PET(Message.SECRET_QUESTION_FIRST_PET),

    /**
     * Secret question about mother's maiden name.
     */
	SECRET_QUESTION_MOTHERS_MAIDEN_NAME(Message.SECRET_QUESTION_MOTHERS_MAIDEN_NAME),
	
	/**
	 * Secret question about user's fav meal
	 */
	SECRET_QUESTION_FAVOURITE_DISH(Message.SECRET_QUESTION_FAVOURITE_DISH),
	
	/**
	 * Unforgettable event in user's life
	 */
	SECRET_QUESTION_UNFORGETTABLE_EVENT(Message.SECRET_QUESTION_UNFORGETTABLE_EVENT);

    private Message value;

    private SecretQuestion(Message message) {
        this.value = message;
    }

}
