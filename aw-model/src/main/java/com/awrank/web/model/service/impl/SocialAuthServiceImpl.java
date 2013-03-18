package com.awrank.web.model.service.impl;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.UserRole;
import com.awrank.web.model.exception.email.EmailAlreadyExistsException;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotFoundByUID;
import com.awrank.web.model.exception.social.SocialEmailNotProvidedException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.service.*;
import com.awrank.web.model.service.impl.pojos.UserSocialRegistrationFormPojo;
import com.awrank.web.model.utils.email.EmailHelper;
import com.awrank.web.model.utils.user.AuditorAwareImpl;
import com.awrank.web.model.utils.user.PasswordUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

/**
 * Service that provide login/registration for social network cases.
 *
 * @author Andrew Stoyaltsev
 */
@Service
public class SocialAuthServiceImpl extends AbstractServiceImpl implements SocialAuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;

	@Autowired
	@Qualifier("userEmailActivationServiceImpl")
	private StateChangeTokenService emailActivationService;

	@Autowired
	@Qualifier("userRoleServiceImpl")
	private UserRoleService userRoleService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	private AuditorAwareImpl auditorAware;

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<String, String> login(UserSocialRegistrationFormPojo userInfo, HttpServletRequest request)
            throws SocialEmailNotProvidedException, EntryPointNotFoundByUID {

		// sometimes social network does not provide us with email data during /userinfo request
		// probably because of a user did not specify his email in social profile.
		if (!StringUtils.hasLength(userInfo.getEmail())) {
			throw new SocialEmailNotProvidedException();
		}

		EntryPoint entryPoint = entryPointService.findOneByUid(userInfo.getNetworkUID());
		if (entryPoint == null) {
            throw new EntryPointNotFoundByUID();
		}

		// log in user
		request.getSession(true);
		// entry history is written in the method below
		auditorAware.setCurrentAuditor(entryPoint);

		// create response
		Map<String, String> response = getPositiveResponse();
		// todo: where csrf token should be taken?
		response.put("csrf_token", "");
		return response;
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map<String, String> register(UserSocialRegistrationFormPojo userInfo, HttpServletRequest request)
			throws SocialEmailNotProvidedException, EmailAlreadyExistsException, UserNotCreatedException {

		// sometimes social network does not provide us with email data during /userinfo request
		if (!StringUtils.hasLength(userInfo.getEmail())) {
            throw new SocialEmailNotProvidedException();
		}

		// check email uniqueness
		if (userService.findOneByEmail(userInfo.getEmail()) != null) {
            throw new EmailAlreadyExistsException();
		}

		// Generate new not used apiKey
		userInfo.setApiKey(userService.getNewApiKey());

		// create user based on given user info from network
		User user = userInfo.createUser();
		user.setCreatedBy(user);
		user.setLastModifiedBy(user);
		user = userService.add(user);

		// create a role
		UserRole role = new UserRole(user);
		role.setCreatedBy(user);
		role.setLastModifiedBy(user);
		userRoleService.save(role);

		// create an entry point
		EntryPoint entryPoint = new EntryPoint();
		entryPoint.setUser(user);
		entryPoint.setPassword(PasswordUtils.hashPassword(user.getApiKey()));
		entryPoint.setUid(userInfo.getNetworkUID());
		// no password because of using OAuth
		entryPoint.setType(userInfo.getNetworkType());
		if (userInfo.isEmailVerified()) {
			entryPoint.setVerifiedDate(LocalDateTime.now());
		}
		entryPoint.setCreatedBy(user);
		entryPoint.setLastModifiedBy(user);
		entryPointService.save(entryPoint);

		if (!userInfo.isEmailVerified()) {
			boolean result = EmailHelper.sendVerificationEmail(
					emailActivationService, user, user.getEmail(), user.getApiKey(),
					userInfo.getUserLocalAddress(), userInfo.getUserRemoteAddress());
			getLogger().info("Verification email sending result: " + result);
		}

		// generate session if one doesn't exist
		request.getSession(true);
		// auto login
		auditorAware.setCurrentAuditor(entryPoint);

		return getPositiveResponse();
	}

}
