package com.awrank.web.model.service.impl;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.StateChangeToken;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.UserRole;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.enums.StateChangeTokenType;
import com.awrank.web.model.exception.AwRankException;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotFoundByUID;
import com.awrank.web.model.exception.user.EmailAlreadyExistsException;
import com.awrank.web.model.exception.user.SocialEmailNotProvidedException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.service.*;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.service.impl.pojos.UserSocialRegistrationFormPojo;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;
import com.awrank.web.model.utils.user.AuditorAwareImpl;
import com.awrank.web.model.utils.user.PasswordUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
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
	private StateChangeTokenService userEmailActivationService;

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
	public Map login(UserSocialRegistrationFormPojo userInfo)
            throws SocialEmailNotProvidedException, EntryPointNotFoundByUID {

		// todo: нужны ли записи в entry_history?

		// sometimes social network does not provide us with email data during /userinfo request
		if (!StringUtils.hasLength(userInfo.getEmail())) {
			throw new SocialEmailNotProvidedException();
		}

		EntryPoint entryPoint = entryPointService.findOneByUid(userInfo.getNetworkUID());
		if (entryPoint == null) {
            throw new EntryPointNotFoundByUID();
		}

		// log in user
		auditorAware.setCurrentAuditor(entryPoint);

		/*
		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(userInfo.getEmail(), userInfo.getApiKey());
		// generate session if one doesn't exist
		HttpSession session = request.getSession();
		AWRankingUserDetails details = new AWRankingUserDetails(entryPoint);
		token.setDetails(details);

		if (!SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
			Authentication authenticatedUser = authenticationManager.authenticate(token);
			SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
			return getPositiveResponseMap("User has been successfully logged in via network!");
		}
		*/

		return null; //getPositiveResponseMap("User is already authenticated!");
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map register(UserSocialRegistrationFormPojo userInfo)
            throws EntryPointNotCreatedException, UserActivationEmailNotSetException,
            UserNotCreatedException, SocialEmailNotProvidedException, EmailAlreadyExistsException {

		// sometimes social network does not provide us with email data during /userinfo request
		if (!StringUtils.hasLength(userInfo.getEmail())) {
            throw new SocialEmailNotProvidedException();
		}

		if (userService.findOneByEmail(userInfo.getEmail()) != null) {
            throw new EmailAlreadyExistsException();
		}

		// avoid double generating
		// todo: this spot of code could be an util method
		String apiKey = UUID.randomUUID().toString();
		while (userService.findByAPIKey(apiKey) != null) {
			apiKey = UUID.randomUUID().toString();
		}
		userInfo.setApiKey(apiKey);

		// todo: we have no password, apiKey is used as temporary solution. need to discuss

		// create user based on given user info from network
		User user = userInfo.createUser();
		user = userService.add(user);

		// create a role
		UserRole role = new UserRole(user);
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
		entryPointService.add(entryPoint);

		if (!userInfo.isEmailVerified()) {
			userInfo.setUserLocalAddress(/*request.getLocalAddr()*/null);
			userInfo.setUserRemoteAddress(/*request.getRemoteAddr()*/null);
			String key;
			try {
				key = SMTPAuthenticator.getHashed256(
						userInfo.getEmail() + "." +
								userInfo.getApiKey() + "." +
								userInfo.getUserLocalAddress() + "." +
								userInfo.getUserRemoteAddress());
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new UserNotCreatedException();
			}

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("localAddr", userInfo.getUserLocalAddress());
			params.put("remoteAddr", userInfo.getUserRemoteAddress());
			params.put("testactivation_email", user.getEmail());
			params.put("testactivation_password", null);

			try {
				userEmailActivationService.send(params);
			} catch (AwRankException e) {
				e.printStackTrace();
			}
			// store to db information about verification email was sent
			StateChangeToken stateChangeToken = new StateChangeToken();
			stateChangeToken.setToken(key);
			stateChangeToken.setType(StateChangeTokenType.USER_EMAIL_VERIFICATION);
			stateChangeToken.setUser(user);
			stateChangeToken.setValue(user.getEmail());
			//Check later if we need remote or local IP here
			stateChangeToken.setIpAddress(userInfo.getUserRemoteAddress());
			userEmailActivationService.save(stateChangeToken);
		}

		// log in just registered user
		UsernamePasswordAuthenticationToken token =
				new UsernamePasswordAuthenticationToken(userInfo.getEmail(), userInfo.getApiKey());
		// generate session if one doesn't exist
		//HttpSession session = request.getSession();
		AWRankingUserDetails details = new AWRankingUserDetails(entryPoint);
		token.setDetails(details);

		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

		return null;//getPositiveResponseMap("Registration via network finished successfully! User stored at session.");
	}

}
