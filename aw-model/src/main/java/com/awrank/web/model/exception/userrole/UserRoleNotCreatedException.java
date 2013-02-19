/**
 *
 */
package com.awrank.web.model.exception.userrole;

import com.awrank.web.model.exception.AwRankException;

/**
 * Thrown in case of {@link com.awrank.web.model.service.impl.UserRoleService#add} was not executed.
 *
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
public class UserRoleNotCreatedException extends AwRankException {

    public UserRoleNotCreatedException() {
        super();
    }

    @Override
    public String getMessage() {
        return "New User Role was not created!";
    }

}
