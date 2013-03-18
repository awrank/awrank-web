package com.awrank.web.model.service;

import com.awrank.web.model.exception.email.EmailAlreadyExistsException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotFoundByUID;
import com.awrank.web.model.exception.social.SocialEmailNotProvidedException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.service.impl.pojos.UserSocialRegistrationFormPojo;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Service which provides methods to be registered or logged in via social networks like Google and Facebook.
 *
 * @author Andrew Stoyaltsev
 */
public interface SocialAuthService extends AbstractService {

	/**
	 * Provides login to the system via social network.
	 *
	 * @param userInfo user profile data taken from social network.
	 * @param request  HTTP servlet request.
	 * @return Map instance to be converted tp positive JSON response.
	 * @throws SocialEmailNotProvidedException
	 *                                 will be thrown in case if the social network did not provide user email.
	 * @throws EntryPointNotFoundByUID will be thrown in case when {@code entry_point} record was not found.
	 */
	Map<String, String> login(UserSocialRegistrationFormPojo userInfo, HttpServletRequest request)
			throws SocialEmailNotProvidedException, EntryPointNotFoundByUID;

	/**
	 * Provides registration in the system using user's social network data.
	 *
	 * @param userInfo user profile data taken from social network.
	 * @param request  HTTP servlet request.
	 * @return Map instance to be converted tp positive JSON response.
	 * @throws UserNotCreatedException     is thrown in case when user was not created because of some problems.
	 * @throws SocialEmailNotProvidedException
	 *                                     will be thrown in case if the social network did not provide user email.
	 * @throws EmailAlreadyExistsException is thrown in case if registration email is not unique and already exists in the system.
	 */
	Map<String, String> register(UserSocialRegistrationFormPojo userInfo, HttpServletRequest request)
			throws UserNotCreatedException, SocialEmailNotProvidedException, EmailAlreadyExistsException;

}
