package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.UserDao;
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
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * todo: Class description
 *
 * @author Andrew Stoyaltsev
 */
@Service
public class SocialAuthServiceImpl extends AbstractServiceImpl implements SocialAuthService {

	@Autowired
	private UserDao userDao;

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

		User user = new User(); // ???
//		List<EntryPoint> list = entryPointService.findOneByEntryPointTypeAndUid(EntryPointType.GOOGLE, uid);
		// 2

		return getNegativeResponseMap("Social Login method is not implemented yet!");
	}

	@Override
	public Map register(UserRegistrationFormPojo userInfo, HttpServletRequest request)
			throws EntryPointNotCreatedException, UserActivationEmailNotSetException, UserNotCreatedException {

		/* Scenario from specification doc:
		1) проверка уникальности email
		2) создается User, UserRole, EntryPoint, UserEmailActivation
		3) запуск активации email
		4) на выходе: пустой объект - {}
		*/

		if (userService.findOneByEmail(userInfo.getEmail()) != null) {
			return getNegativeResponseMap("This email is already registered in the system!");
		}

		// todo: borrowed from UserController
		if (userService.findByAPIKey(userInfo.getApiKey()) != null) {
			return getNegativeResponseMap("This apikey is already registered in the system!");
		}

		userInfo.setUserLocalAddress(request.getLocalAddr());
		userInfo.setUserRemoteAddress(request.getRemoteAddr());

		// no password when use OAuth
		//final String plainPassword = userInfo.getPassword();
		//userInfo.setPassword(PasswordUtils.hashPassword(userInfo.getPassword()));

		//User user = userService.register(userInfo, request);

		//---- create user ----
		User user = new User();
		user.setApiKey(UUID.randomUUID().toString());
		user.setFirstName(userInfo.getFirstName());
		user.setLastName(userInfo.getLastName());
		user.setEmail(userInfo.getEmail());
		user.setLanguage(userInfo.getLanguage());
		user.setAuthorizationFailsCount(0); //?
		user.setBirthday(userInfo.getBirthday());
		userService.add(user);

		//---- here we need save a role for user ----
		UserRole role = new UserRole();
		role.setUser(user);
		role.setRole(Role.ROLE_USER);
		userRoleService.save(role);

		//---- create entrance point for him ----
		EntryPoint entryPoint = new EntryPoint();
		entryPoint.setUser(user);
		entryPoint.setUid(userInfo.getNetworkUID());
		// we do not know password because of using OAuth
		entryPoint.setType(userInfo.getNetworkType());
		entryPointService.add(entryPoint);

		if (!userInfo.isEmailVerified()) {
			//---- sending verification email ----
			String key;
			try {
				// instead of password - networkUID is used
				key = SMTPAuthenticator.getHashed256(
						userInfo.getEmail() + "." +
								userInfo.getNetworkUID() + "." +
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
				// todo: add LOG
				e.printStackTrace();
			}
			//---- store to db information about verification email was sent ----
			StateChangeToken stateChangeToken = new StateChangeToken();
			stateChangeToken.setToken(key);
			stateChangeToken.setType(StateChangeTokenType.USER_EMAIL_VERIFICATION);
			stateChangeToken.setUser(user);
			stateChangeToken.setValue(user.getEmail());
			stateChangeToken.setIpAddress(userInfo.getUserRemoteAddress());//Check later if we need remote or local IP here
			userEmailActivationService.save(stateChangeToken);
		}

		return getPositiveResponseMap("Registration via Google finished successfully!");
	}

}
