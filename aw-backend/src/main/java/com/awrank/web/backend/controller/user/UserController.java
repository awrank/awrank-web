package com.awrank.web.backend.controller.user;

import com.awrank.web.backend.authentication.AWRankingUserDetails;
import com.awrank.web.backend.authentication.AWRankingUserDetailsService;
import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserEmailActivationService;
import com.awrank.web.model.service.UserRoleService;
import com.awrank.web.model.service.UserService;
import com.awrank.web.model.service.impl.UserServiceImpl;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * The stub for work with users
 *
 * @author Olga Korokhina
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends AbstractController {

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("userRoleServiceImpl")
	private UserRoleService userRoleService;

	@Autowired
	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;

	@Autowired
	private UserEmailActivationService userEmailActivationService;

	@Autowired
	private AWRankingUserDetailsService awRankingUserDetailsService;

	@Autowired
	@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	private Map getPositiveResponseMap() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "ok");
		return result;
	}

	private Map getNegativeResponseMap(String reason) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "failure");
		result.put("reason", reason);
		return result;
	}

	/**
	 * Here we go from registration form so, technically, this is "/register"
	 *
	 * @param form
	 * @param request
	 * @return
	 * @throws EntryPointNotCreatedException
	 * @throws UserActivationEmailNotSetException
	 *
	 */
	@RequestMapping(
			value = "/add",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
	Map addUser(@ModelAttribute UserRegistrationFormPojo form, HttpServletRequest request)
			throws EntryPointNotCreatedException, UserActivationEmailNotSetException {

		if (userService.findOneByEmail(form.getEmail()) != null) {
			return getNegativeResponseMap("This email is already registered in the system!");
		}

		if (userService.findByAPIKey(form.getApiKey()) != null) {
			return getNegativeResponseMap("This apikey is already registered in the system!");
		}

		form.setUserLocalAddr(request.getLocalAddr());
		form.setUserRemoteAddr(request.getRemoteAddr());

		try {
			User user = userService.register(form, request);
			//if registered ok - we need to log user in manualy

			//---------- we need some authorization for register user + he is logged in right after it -------
			//----- we need to do it here manually-----


			// AWRankingGrantedAuthority[] grantedAuthorities = new AWRankingGrantedAuthority[] { new AWRankingGrantedAuthority(user.getId(), "ROLE_USER") };

			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(form.getEmail(), form.getPassword());//, grantedAuthorities);

			// generate session if one doesn't exist
			request.getSession();
/*
			awRankingUserDetailsService.setUserService(userService);
			awRankingUserDetailsService.setUserRoleService(userRoleService);
			awRankingUserDetailsService.setEntryPointService(entryPointService);
*/
			AWRankingUserDetails details = awRankingUserDetailsService.createUserDetailsForUserByCredentials(user, form.getPassword(), EntryPointType.EMAIL);

			token.setDetails(details);

			Authentication authenticatedUser = authenticationManager.authenticate(token);

			SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

			return getPositiveResponseMap();

		} catch (UserNotCreatedException e) {
			e.printStackTrace();
			return getNegativeResponseMap(e.getMessage());
		}

	}

	//headers = "Accept=application/json"
	@RequestMapping(
			value = "/delete",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	//@Consumes("application/json")
	public
	@ResponseBody
	Map deleteUser(@RequestBody User user) {
		if (userService.findOneByEmail(user.getEmail()) == null) {
			return getNegativeResponseMap("User with this email is not registered in the system!");
		}
		if (userService.findOne(user.getId()) == null) {
			return getNegativeResponseMap("user with this ID not registered in system");
		}

		try {
			userService.delete(user);
			return getPositiveResponseMap();
		} catch (UserNotDeletedException e) {

			e.printStackTrace();
			return getNegativeResponseMap(e.getMessage());
		}
	}


	@RequestMapping(method = RequestMethod.GET, value = "/verifyemail/{key}")
	public
	@ResponseBody
	Map verifyTestEmail(@PathVariable("key") String key, HttpServletRequest request) throws Exception {

		Boolean response = userEmailActivationService.verify(key, request);

		if (response == false) return getNegativeResponseMap("not verified");
		else return getPositiveResponseMap();

	}

	//------------------- refactor out it not needed ---------------

	public void setUserService(UserServiceImpl value) {

		userService = value;
	}

	public UserService getUserService() {

		return userService;
	}
}
