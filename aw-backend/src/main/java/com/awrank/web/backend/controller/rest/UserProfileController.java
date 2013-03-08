package com.awrank.web.backend.controller.rest;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.enums.StateChangeTokenType;
import com.awrank.web.model.exception.AwRankException;
import com.awrank.web.model.exception.entrypoint.EntryPointByEmailNotFoundException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.service.*;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;
import com.awrank.web.model.utils.user.AuditorAwareImpl;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller exposes basic methods to work with user profile (with user access particular)
 * through REST.
 *
 * @author Olga Korokhina
 */
@Controller
@RequestMapping(value = "/rest/profile")
public class UserProfileController extends AbstractController {

	@Autowired
	@Qualifier("entryHistoryServiceImpl")
	private EntryHistoryService entryHistoryService;

	@Autowired
	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	private UserPasswordChangingService userPasswordChangingService;

	@Autowired
	AuditorAwareImpl auditorAware;

	@Resource(name = "userEmailActivationServiceImpl")
	private StateChangeTokenService userEmailActivationService;

	/**
	 * Get the access history for currently logged in user, used Principal as a param
	 *
	 * @param model
	 * @param pageable
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/accesshistory",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json")
	public
	@ResponseBody()
	Page<EntryHistory> getUserLoginHistory(ModelMap model, @PageableDefaults(pageNumber = 0, value = 100) Pageable pageable, Principal principal) {

		AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
		Page<EntryHistory> allEntryHistory = entryHistoryService.getPageByUserId(details.getUserId(), pageable);

		model.addAttribute("result", allEntryHistory.getContent());

		return allEntryHistory;
	}

	/**
	 * Here user goes after clicking on reset password email link
	 *
	 * @param request
	 * @param inMap
	 * @return
	 * @throws EntryPointByEmailNotFoundException
	 *
	 */
	@RequestMapping(value = "/password/new", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	public Map recoveryPasswordIntoEmail(HttpServletRequest request, @RequestBody Map<String, String> inMap) throws EntryPointByEmailNotFoundException {
		String email = inMap.get("email");
		userService.recoveryPasswordIntoEmail(email, request.getLocalAddr(), request.getRemoteAddr());
		return getPositiveResponseMap();
	}

	/**
	 * Simple manual change oif password - user shall be logged in and enter current password
	 *
	 * @param request
	 * @param inMap
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/password/change", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	public
	@ResponseBody()
	Map changePassword(HttpServletRequest request, @ModelAttribute Map<String, String> inMap) throws Exception {
		String old_password = inMap.get("old_password");
		String new_password = inMap.get("new_password");

		AWRankingUserDetails details = auditorAware.getCurrentUserDetails();
		userService.changePassword(details.getUid(), old_password, new_password);

		throw UnauthorizedException.getInstance();
	}

	/**
	 * Simple manual change of email - user shall be logged in, on new email verification link will be sent
	 *
	 * @param form
	 * @param model
	 * @param request
	 * @param principal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeemailmanual",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json")
	public
	@ResponseBody()
	Map setUserNewEmaildManual(@ModelAttribute UserRegistrationFormPojo form, ModelMap model, HttpServletRequest request, Principal principal) throws Exception {

		if (principal == null) return getNegativeResponseMap("You have to be logged in to perform this operation");

		AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

//		---------------- sending verification email --------------------

		String key;
		try {
			key = SMTPAuthenticator.getHashed256(details.getUserEmail() + "." + details.getPassword() + "." + request.getLocalAddr() + "." + request.getRemoteAddr());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new UserNotCreatedException();
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("localAddr", request.getLocalAddr());
		params.put("remoteAddr", request.getRemoteAddr());
		params.put("testactivation_email", details.getUserEmail());
		params.put("testactivation_password", details.getPassword());

		try {
			userEmailActivationService.send(params);
		} catch (AwRankException e) {
//			TODO Auto-generated catch block
			getLogger().error(e.getMessage(), e);
		}

//		-------------- saving to db -----------------------------

		StateChangeToken token = new StateChangeToken();
		token.setToken(key);
		token.setType(StateChangeTokenType.USER_EMAIL_CHANGE);
		token.setCreatedBy(new User(details.getUserId()));
		token.setIpAddress(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getRemoteAddress());//or use taken from request one here?
		token.setNewValue(form.getEmail());
		token.setValue(details.getUserEmail());

		userEmailActivationService.save(token);

		return getPositiveResponseMap("Verification link sent to new email, untill it will be verifird current email is valid");
	}

	/**
	 * Handler for password changing - if token found redirect to passwrd changing form
	 *
	 * @param key
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/changepassword/{key}")
	public String verifyTestEmail(@PathVariable("key") String key, HttpServletRequest request, ModelMap modelMap) throws Exception {

		Boolean response = userPasswordChangingService.verify(key, request);

		if (response == false) {

			modelMap.addAttribute("params", getNegativeResponseMap("not found password changing token"));
			return "403";

		} else {

			//-------- here we log user in with current credentials for Principal can be used in newpassword

			StateChangeToken vtoken = userPasswordChangingService.findByCode(key);
			User user = vtoken.getUser();

			EntryPoint entryPoint = entryPointService.findOneByEntryPointTypeAndUid(EntryPointType.EMAIL, user.getEmail());

			//TODO request.getRemoteAddr(), request.getSession().getId()
			auditorAware.setCurrentAuditor(entryPoint);

			modelMap.addAttribute("params", getPositiveResponseMap());

			//--------- current entry point is no longer valid ----------

			entryPoint.setEndedDate(LocalDateTime.now());
			entryPointService.save(entryPoint);

			return "passwordchangingform";//here we shall create new entry point
		}
	}

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
	 * Request password change. Think about if we shall also see what is in Principal and if user logged in and has no
	 * ROLE_ADMIN and specified another email reject this request?
	 *
	 * @param form
	 * @param model
	 * @param principal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/resetpassword",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json")
	public
	@ResponseBody()
	Map sendUserResetPasswordLink(@ModelAttribute UserRegistrationFormPojo form, ModelMap model, Principal principal, HttpServletRequest request) throws Exception {
		//---------- check if we are logged in user ----------
		if (principal != null) {
			AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
			ArrayList<Role> list = (ArrayList<Role>) details.getAuthorities();

			Boolean isAdmin = false;
			Boolean isUserEmailVerified = false;

			for (Role auth : list) {

				if (auth == Role.ROLE_ADMIN) isAdmin = true;
				if (auth == Role.ROLE_USER_VERIFIED) isUserEmailVerified = true;

			}

			String email = details.getUsername();//actually this is an email

			if (!isAdmin) {

				if ((email.compareTo(form.getEmail()) != 0) || !isUserEmailVerified) {//if user is not an admin, enter not his email or email is his but not verified

					return getNegativeResponseMap("You are not autorized to change password for user with given email");
				}
			}
		}

		//-------- not logged in or an admin so send the link -------------------

		User user = userService.findOneByEmail(form.getEmail());
		String testactivation_password = null;
		for (EntryPoint entryPoint : user.getEntryPoints()) {
			if (entryPoint.getType() == EntryPointType.EMAIL) {
				testactivation_password = entryPoint.getPassword();
				break;
			}
		}
		if (testactivation_password == null) return getNegativeResponseMap("No current password");

		StateChangeToken stateChangeToken = new StateChangeToken();

		String key = SMTPAuthenticator.getHashed256(form.getEmail() + "." + testactivation_password + "." + request.getLocalAddr());

		stateChangeToken.setToken(key);
		stateChangeToken.setType(StateChangeTokenType.USER_PASSWORD_CHANGE);
		stateChangeToken.setUser(user);
		stateChangeToken.setValue(user.getEmail());
		stateChangeToken.setIpAddress(request.getLocalAddr());//Check later if we need remote or local IP here

		userPasswordChangingService.save(stateChangeToken);

		//-------------- and send link via email -----------------------

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("localAddr", request.getLocalAddr());
		params.put("testactivation_password", testactivation_password);
		params.put("testactivation_email", form.getEmail());

		userPasswordChangingService.send(params);

		return getPositiveResponseMap("password changing link sent to " + form.getEmail());
	}
}
