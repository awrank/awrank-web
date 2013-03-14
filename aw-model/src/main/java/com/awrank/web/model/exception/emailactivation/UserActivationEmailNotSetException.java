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

    public UserActivationEmailNotSetException() {
        super();
    }

    @Override
    public String getMessage() {
        return "User activation email was not sent";
    }

}
