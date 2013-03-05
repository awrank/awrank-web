package com.awrank.web.backend.controller.user;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.*;
import com.awrank.web.model.service.impl.UserServiceImpl;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import com.awrank.web.model.utils.user.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * The stub for work with users
 *
 * @author Olga Korokhina
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends AbstractController {

	@Autowired
//	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
//	@Qualifier("userRoleServiceImpl")
	private UserRoleService userRoleService;

	@Autowired
//	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;

	//	@Autowired
	//@Qualifier("userEmailActivationServiceImpl")
	@Resource(name = "userEmailActivationServiceImpl")
	private StateChangeTokenService userEmailActivationService;

	@Autowired
	//@Qualifier("authenticationManager")
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;

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
			throws EntryPointNotCreatedException, UserActivationEmailNotSetException, UserNotCreatedException {
		
		if (userService.findOneByEmail(form.getEmail()) != null) {
			return getNegativeResponseMap("This email is already registered in the system!");
		}
		
		//and here we generate the API key
		
		String apiKey = UUID.randomUUID().toString();
		while(userService.findByAPIKey(apiKey) != null) apiKey = UUID.randomUUID().toString();
		form.setApiKey(apiKey);
		
		form.setUserLocalAddress(request.getLocalAddr());
		form.setUserRemoteAddress(request.getRemoteAddr());
		final String plainPassword = form.getPassword();
		form.setPassword(PasswordUtils.hashPassword(form.getPassword()));
		
		EntryPoint entryPoint = userService.register(form, request);

		//---------- we need some authorization for register user + he is logged in right after it -------

		// AWRankingGrantedAuthority[] grantedAuthorities = new AWRankingGrantedAuthority[] { new AWRankingGrantedAuthority(user.getId(), "ROLE_USER") };

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(form.getEmail(), plainPassword);

		// generate session if one doesn't exist
		request.getSession();
		AWRankingUserDetails details = userDetailsService.fillUserDetails(entryPoint);
		token.setDetails(details);

		Authentication authenticatedUser = authenticationManager.authenticate(token);
		SecurityContextHolder.getContext().setAuthentication(authenticatedUser);

		return getPositiveResponseMap("Verification email was sent to your email");
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
	Map deleteUser(@RequestBody User user) throws UserNotDeletedException {
		if (userService.findOneByEmail(user.getEmail()) == null) {
			return getNegativeResponseMap("User with this email is not registered in the system!");
		}
		if (userService.findOne(user.getId()) == null) {
			return getNegativeResponseMap("user with this ID not registered in system");
		}

		userService.delete(user);
		return getPositiveResponseMap();
	}


	@RequestMapping(method = RequestMethod.GET, value = "/verifyemail/{key}")
	public
	@ResponseBody
	Map verifyTestEmail(@PathVariable("key") String key, HttpServletRequest request) throws Exception {
		boolean response = userEmailActivationService.verify(key, request);
		return response ? getPositiveResponseMap() : getNegativeResponseMap("not verified");
	}

	//------------------- refactor out it not needed ---------------

	public void setUserService(UserServiceImpl value) {
		userService = value;
	}

	public UserService getUserService() {
		return userService;
	}
}
