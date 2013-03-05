package com.awrank.web.model.service;

import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * todo: Class description
 *
 * @author Andrew Stoyaltsev
 */
public interface SocialAuthService extends AbstractService {

	Map login(UserRegistrationFormPojo userInfo, HttpServletRequest request);

	Map register(UserRegistrationFormPojo userInfo, HttpServletRequest request)
			throws EntryPointNotCreatedException, UserActivationEmailNotSetException, UserNotCreatedException;

}
