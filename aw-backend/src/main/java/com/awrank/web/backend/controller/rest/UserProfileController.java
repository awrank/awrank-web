package com.awrank.web.backend.controller.rest;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.enums.StateChangeTokenType;
import com.awrank.web.model.exception.AwRankException;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointByEmailNotFoundException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.service.*;
import com.awrank.web.model.service.impl.pojos.UserProfileDataFormPojo;
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
	private UserProfileService userProfileService;
	
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
		
		Page<EntryHistory> allEntryHistory = userProfileService.getUserLoginHistory(pageable, principal);
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
	 * Filld ModelMap instance with currently logged in user data (in getUserDataInForm userdata) and redirects to "userdata" page
	 * @param modelMap
	 * @param principal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userdata/get",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json")
	public
	@ResponseBody()
	String getUserDataInForm(ModelMap modelMap, Principal principal) throws Exception {

		if (principal == null) return "403";
		modelMap.addAttribute("userdata", userProfileService.getUserProfileDataForPrincipal(principal));

		return "userdata";
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
	Map setUserNewEmaildManual(@ModelAttribute UserRegistrationFormPojo form, ModelMap model, HttpServletRequest request, Principal principal) throws UserActivationEmailNotSetException {

		if (principal == null) return getNegativeResponseMap("You have to be logged in to perform this operation");
		
		this.userProfileService.sendNewEmailVerificationLinkOnEmailManualChange(form, request, principal);

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
	public String verifyPasswordChangingFromEmailLink(@PathVariable("key") String key, HttpServletRequest request, ModelMap modelMap) throws Exception {

		EntryPoint entryPoint  = userPasswordChangingService.verify(key, request);

		if (entryPoint == null) {

			modelMap.addAttribute("params", getNegativeResponseMap("not found password changing token"));
			return "403";

		} else {

			//-------- here we log user in with current credentials for Principal can be used in newpassword
			
			//TODO request.getRemoteAddr(), request.getSession().getId()
			auditorAware.setCurrentAuditor(entryPoint);

			modelMap.addAttribute("params", getPositiveResponseMap());

			//--------- current entry point is no longer valid ----------

			entryPoint.setEndedDate(LocalDateTime.now());
			entryPointService.save(entryPoint);

			//---- redirect user to form where he can enter new password -------
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
		
		return this.userProfileService.sendPasswordChangingLinkToEmail(form, request, principal);
	}
}
