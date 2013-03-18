package com.awrank.web.backend.controller.user;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.Diary;
import com.awrank.web.model.domain.DiaryEvent;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.DiaryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.StateChangeTokenService;
import com.awrank.web.model.service.UserService;
import com.awrank.web.model.service.impl.UserServiceImpl;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.utils.user.AuditorAwareImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
	private UserService userService;

	@Resource(name = "userEmailActivationServiceImpl")
	private StateChangeTokenService userEmailActivationService;

	@Autowired
	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;
	
	@Autowired
	AuditorAwareImpl auditorAware;
	
	private Map getPositiveResponseMap() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "ok");
		return result;
	}

	private Map getPositiveResponseMap(String resultStr) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "ok");
		result.put("reason", resultStr);
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
	
	@RequestMapping(value = "/add2", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	public
	@ResponseBody()
	Map addUser2(@RequestBody Map<String, String> in, HttpServletRequest request)
			throws EntryPointNotCreatedException, UserActivationEmailNotSetException, UserNotCreatedException {

		UserRegistrationFormPojo form = new UserRegistrationFormPojo();
		
		form.fillWith(in);
		
		if (userService.findOneByEmail(form.getEmail()) != null) {
			return getNegativeResponseMap("EMAIL_ALREADY_EXISTS");
		}
		
		//and here we generate the API key

		String apiKey = UUID.randomUUID().toString();
		while (userService.findByAPIKey(apiKey) != null) apiKey = UUID.randomUUID().toString();
		form.setApiKey(apiKey);

		form.setUserLocalAddress(request.getLocalAddr());
		form.setUserRemoteAddress(request.getRemoteAddr());

		EntryPoint entryPoint = userService.register(form, request);

		// generate session if one doesn't exist
		request.getSession();

		auditorAware.setCurrentAuditor(entryPoint);

		return getPositiveResponseMap("YOU_REGISTERED_SUCCESSFULLY_VERIFICATION_EMAIL_SENT");
	}
	
	
	
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
		while (userService.findByAPIKey(apiKey) != null) apiKey = UUID.randomUUID().toString();
		form.setApiKey(apiKey);

		form.setUserLocalAddress(request.getLocalAddr());
		form.setUserRemoteAddress(request.getRemoteAddr());
//		final String plainPassword = form.getPassword();
//		form.setPassword(PasswordUtils.hashPassword(plainPassword));

		EntryPoint entryPoint = userService.register(form, request);

		// generate session if one doesn't exist
		request.getSession();

		auditorAware.setCurrentAuditor(entryPoint);

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
		
		//---- if verified we have to relogin user - with new roles ----
		
		EntryPoint entryPoint = userEmailActivationService.verify(key, request);

		if(entryPoint == null) return  getNegativeResponseMap("not verified");
		// generate session if one doesn't exist
		request.getSession();

		auditorAware.setCurrentAuditor(entryPoint);
		
		return getPositiveResponseMap();
	}

	//------------------- refactor out it not needed ---------------

	public void setUserService(UserServiceImpl value) {
		userService = value;
	}

	public UserService getUserService() {
		return userService;
	}
}
