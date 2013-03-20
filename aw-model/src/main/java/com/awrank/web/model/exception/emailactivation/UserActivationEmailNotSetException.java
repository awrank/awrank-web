/**
 *
 */
package com.awrank.web.model.exception.emailactivation;

import com.awrank.web.model.exception.AwRankException;

/**
 * Thrown in case of {@link com.awrank.web.model.service.impl.UserEmailActivationService#add} was not executed.
 *
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
public class UserActivationEmailNotSetException extends AwRankException {
	
	public static String USER_EMAIL_ACTIVATION_NOT_SENT = "USER_EMAIL_ACTIVATION_NOT_SENT";

    public UserActivationEmailNotSetException() {
        super();
    }

    @Override
    public String getMessage() {
        return USER_EMAIL_ACTIVATION_NOT_SENT;
    }

}
