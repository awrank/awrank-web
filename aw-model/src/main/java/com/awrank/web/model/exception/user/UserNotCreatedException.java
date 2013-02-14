/**
 *
 */
package com.awrank.web.model.exception.user;

import com.awrank.web.model.exception.AwRankException;

/**
 * Thrown in case of {@link com.awrank.web.model.service.impl.UserServiceImpl#add} was not executed.
 *
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
public class UserNotCreatedException extends AwRankException {

    public UserNotCreatedException() {
        super();
    }

    @Override
    public String getMessage() {
        return "New user was not created!";
    }

}
