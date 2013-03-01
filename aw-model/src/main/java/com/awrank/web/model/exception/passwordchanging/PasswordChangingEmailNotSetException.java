/**
 *
 */
package com.awrank.web.model.exception.passwordchanging;

import com.awrank.web.model.exception.AwRankException;

/**
 * Thrown in case of {@link com.awrank.web.model.service.impl.UserPasswordChangingService#add} was not executed.
 *
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
public class PasswordChangingEmailNotSetException extends AwRankException {

    public PasswordChangingEmailNotSetException() {
        super();
    }

    @Override
    public String getMessage() {
        return "Password changing email was not sent";
    }

}
