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
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.service.*;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;
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

	// TODO: Duplicate from UserController. Should be refactored in both places
	private Map getPositiveResponseMap() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "ok");
		return result;
	}

	private Map getPositiveResponseMap(String resultStr) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", resultStr);
		return result;
	}

	private Map getNegativeResponseMap(String reason) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "failure");
		result.put("reason", reason);
		return result;
	}
	//--------

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map login(UserRegistrationFormPojo userInfo, HttpServletRequest request) {
		/* Scenario from specification doc
		1) поиск точки входа (EntryPoint) с соответствующими параметрами:
		-- точка входа должна быть подтверждена verifiedDate not null,
		-- type== google
		-- uid==${id}
		2) определение пользователя в сессии, иначе - ошибка
		3) записать сессию текущего пользователя в список залогиненых пользователей;
		если в этом списке уже есть данный пользователь, старую сессию необходимо закрыть
		4) на выходе: token для защиты от CSRF атак - {token:"xxx"}
		 */

		// sometimes social network does not provide us with email data during /userinfo request
		if (!StringUtils.hasLength(userInfo.getEmail())) {
			return getNegativeResponseMap("Unfortunately the social network you've chosen did not provide us " +
					"with your email. Please set email in your social account and try again or " +
					"use the standard registration form. Thanks for understanding.");
		}

		EntryPoint entryPoint = entryPointService.findOneByUid(userInfo.getNetworkUID());

		if (entryPoint == null) {
			return getNegativeResponseMap("Unfortunately, we could not find entry point for " +
					"networkType=" + userInfo.getNetworkType() + "; networkUID=" + userInfo.getNetworkUID());
		}

		// log in user
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

		return getPositiveResponseMap("User is already authenticated!");
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Map register(UserRegistrationFormPojo userInfo, HttpServletRequest request)
			throws EntryPointNotCreatedException, UserActivationEmailNotSetException, UserNotCreatedException {

		/* Scenario from specification doc:
		-----------------------------------
		0)* check if emails exist, if no - offer user to use standard registration form.
		1) проверка уникальности email
		2) создается User, UserRole, EntryPoint, UserEmailActivation
		3) запуск активации email
		4) на выходе: пустой объект - {}
		-----------------------------------
		* - new plan points
		*/

		// sometimes social network does not provide us with email data during /userinfo request
		if (!StringUtils.hasLength(userInfo.getEmail())) {
			return getNegativeResponseMap("Unfortunately the social network you've chosen did not provide us " +
					"with your email. Please set email in your social account and try again or " +
					"use the standard registration form. Thanks for understanding.");
		}

		if (userService.findOneByEmail(userInfo.getEmail()) != null) {
			return getNegativeResponseMap("This email is already registered in the system!");
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
			userInfo.setUserLocalAddress(request.getLocalAddr());
			userInfo.setUserRemoteAddress(request.getRemoteAddr());
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
		HttpSession session = request.getSession();
		AWRankingUserDetails details = new AWRankingUserDetails(entryPoint);
		token.setDetails(details);

		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

		return getPositiveResponseMap("Registration via network finished successfully! User stored at session.");
	}

}
