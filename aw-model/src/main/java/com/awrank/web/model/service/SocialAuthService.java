package com.awrank.web.model.service;

import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotFoundByUID;
import com.awrank.web.model.exception.user.EmailAlreadyExistsException;
import com.awrank.web.model.exception.user.SocialEmailNotProvidedException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.service.impl.pojos.UserSocialRegistrationFormPojo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * todo: Class description
 *
 * @author Andrew Stoyaltsev
 */
public interface SocialAuthService extends AbstractService {

	Map login(UserSocialRegistrationFormPojo userInfo) throws SocialEmailNotProvidedException, EntryPointNotFoundByUID;

	Map register(UserSocialRegistrationFormPojo userInfo) throws
            EntryPointNotCreatedException, UserActivationEmailNotSetException,
            UserNotCreatedException, SocialEmailNotProvidedException,
            EmailAlreadyExistsException;

}
