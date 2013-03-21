package com.awrank.web.backend.controller.rest;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointByEmailNotFoundException;
import com.awrank.web.model.service.*;
import com.awrank.web.model.service.impl.pojos.UserNewPasswordFormPojo;
import com.awrank.web.model.service.impl.pojos.UserProfileDataFormPojo;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import com.awrank.web.model.utils.user.AuditorAwareImpl;
import com.awrank.web.model.utils.user.PasswordUtils;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
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
	String getUserDataInForm(ModelMap modelMap, Principal principal) throws Exception {

		if (principal == null) return "403";
		modelMap.addAttribute("userdata", userProfileService.getUserProfileDataForPrincipal(principal));

		return "userdata";
	}
	
	/**
	 * Called from divProfile to fetch user data
	 * @param principal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userdata/get2",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json")
	public
	@ResponseBody Map getUserDataAsMap(HttpServletRequest request, Principal principal) throws Exception {

		if (principal == null) return this.getNegativeResponseMap("ERROR_ACCESS");
		
		UserProfileDataFormPojo userdata =  userProfileService.getUserProfileDataForPrincipal(principal);
		userdata.setLocalIP(request.getLocalAddr());
		userdata.setRemoteIP(request.getRemoteAddr());
		Map userdataMap = userdata.toMap();
		userdataMap.put("result", "ok");
		return userdataMap;
	}
	
	/**
	 * Old user profile form target
	 * @param userdata
	 * @param principal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userdata/update",
			method = RequestMethod.POST,
			produces = "application/json")
	public
	@ResponseBody()
	Map updateUserData(@ModelAttribute UserProfileDataFormPojo userdata, Principal principal) throws Exception {
		
		return this.userProfileService.updateProfileData(userdata, principal);
	}

	
	/**
	 * Called from new beautiful profile
	 * @param principal
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/userdata/update2",method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	public
	@ResponseBody Map updateUserData2(@RequestBody Map<String, String> in, Principal principal) throws Exception {

		if (principal == null) return this.getNegativeResponseMap("ERROR_ACCESS");
		
		UserProfileDataFormPojo userdata =  new UserProfileDataFormPojo();
		userdata.fillWith(in);
		
		return this.userProfileService.updateProfileData(userdata, principal);
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
	 * Called from beautiful page
	 * @param form
	 * @param model
	 * @param request
	 * @param principal
	 * @return
	 * @throws UserActivationEmailNotSetException
	 */
	@RequestMapping(value = "/changeemailmanual2",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json")
	public
	@ResponseBody()
	Map setUserNewEmaildManual2(@RequestBody Map<String, String> in, HttpServletRequest request, Principal principal) throws UserActivationEmailNotSetException {
		
		if (principal == null) return getNegativeResponseMap("ERROR_ACCESS");

		//--------- user can change ONLY VERIFIED email! check it here ------
		AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
		User user = userService.findOne(details.getUserId());
		
		EntryPoint currentPoint = entryPointService.findOneByEntryPointTypeAndUid(EntryPointType.EMAIL, user.getEmail());
		if(currentPoint == null)  return getNegativeResponseMap("YOU_HAVE_TO_VERIFY_YOUR_CURRENT_EMAIL_FIRST");
		//-------------------------------
		
		UserRegistrationFormPojo form = new UserRegistrationFormPojo();
		form.setEmail(in.get("email")); 
		form.setLocalIP(in.get("localIP"));
		form.setRemoteIP(in.get("remoteIP"));
		return this.userProfileService.sendNewEmailVerificationLinkOnEmailManualChange(form, principal);

	}

	/**
	 * Handler for password changing - if token found redirect to password changing form
	 *
	 * @param key
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/changepassword/{key}")
	public ModelAndView verifyPasswordChangingFromEmailLink2(@PathVariable("key") String key, HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView("passwordChangeVerificationResponse");
		
		//---- if verified we have to relogin user - with new password ----
		
		EntryPoint entryPoint = userPasswordChangingService.verify(key, request);

		if(entryPoint == null){
			mav.addObject("responseMap", getNegativeResponseMap("PASSWORD_NOT_VERIFIED"));
			return  mav;
		}
		
		auditorAware.setCurrentAuditor(entryPoint);
		
		mav.addObject("responseMap", getPositiveResponseMap("PASSWORD_VERIFIED_SUCCESSFULLY"));
		return mav;
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/forgotpassword/{key}")
	public ModelAndView showForgotPasswordNewPasswordForm(@PathVariable("key") String key, HttpServletRequest request) throws Exception {
		
		ModelAndView mav = new ModelAndView("forgotPasswordNewPasswordForm");
		
		//---- if verified we have to relogin user - with new password ----
		
		EntryPoint entryPoint = userPasswordChangingService.verify(key, request);

		if(entryPoint == null){
			mav.addObject("responseMap", getNegativeResponseMap("FORGOT_PASSWORD_LINK_NOT_VERIFIED"));
			return  mav;
		}
		
		auditorAware.setCurrentAuditor(entryPoint);//TODO: investigate if we need to log user in
		mav.addObject("entryPointId", entryPoint.getId());
		mav.addObject("localIP", request.getLocalAddr());
		mav.addObject("remoteIP", request.getRemoteAddr());
		mav.addObject("responseMap", getPositiveResponseMap("FORGOT_PASSWORD_VERIFIED_SUCCESSFULLY"));
		return mav;
		
	}
	
	/**
	 * Old
	 * @param key
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws Exception
	 */
	//@RequestMapping(method = RequestMethod.GET, value = "/changepassword/{key}")
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
	 * Called from divProfile - 3 fields form for manual password change below
	 * @param in
	 * @param request
	 * @param principal
	 * @return
	 * @throws UserActivationEmailNotSetException
	 */
	@RequestMapping(value = "/changepasswordmanual2",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json")
	public
	@ResponseBody()
	Map setUserNewPassworddManual2(@RequestBody Map<String, String> in, HttpServletRequest request, Principal principal) throws UserActivationEmailNotSetException {
		
		if (principal == null) return getNegativeResponseMap("ERROR_ACCESS");

		//--------- user can change password ONLY VERIFIED email! check it here ------
		AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
		User user = userService.findOne(details.getUserId());
		
		if(in.get("password") != null){//manual reset
		
			EntryPoint currentPoint = entryPointService.findOneByEntryPointTypeAndUid(EntryPointType.EMAIL, user.getEmail());
			if(currentPoint == null)  return getNegativeResponseMap("YOU_HAVE_TO_VERIFY_YOUR_CURRENT_EMAIL_FIRST_PASSWORD");
			//-------------------------------
			String typedPassword = PasswordUtils.hashPassword(in.get("password"));
			if(details.getPassword().compareTo(typedPassword) != 0) return getNegativeResponseMap("ENTERED_WRONG_CURRENT_PASSWORD");
			
			UserNewPasswordFormPojo form = new UserNewPasswordFormPojo();
			form.setCurrentPassword(in.get("password"));
			form.setPassword(in.get("newpassword"));
			form.setPasswordConfirm(in.get("newconfirmation"));
			form.setLocalIP(in.get("localIP"));
			form.setRemoteIP(in.get("remoteIP"));
			return this.userProfileService.sendPasswordChangingLinkToEmail(form, principal);
		}
		else if(in.get("entrypointid") != null){//reset from "forgotPasswordNewPasswordForm" form after forgot password verification link verified
			
			EntryPoint currentPoint = entryPointService.findOneByEntryPointTypeAndUid(EntryPointType.EMAIL, user.getEmail());
			
			if(currentPoint == null)  return getNegativeResponseMap("YOU_HAVE_TO_VERIFY_YOUR_CURRENT_EMAIL_FIRST_PASSWORD");
			
			Long fromForm = new Long(in.get("entrypointid"));
			Long fromPoint = currentPoint.getId();
			if(fromForm.equals(fromPoint) == false) return getNegativeResponseMap("ERROR_ACCESS");
			
			//-------------------------------
			
			UserNewPasswordFormPojo form = new UserNewPasswordFormPojo();
			form.setCurrentPassword(currentPoint.getPassword());
			form.setPassword(in.get("newpassword"));
			form.setPasswordConfirm(in.get("newconfirmation"));
			form.setLocalIP(in.get("localIP"));
			form.setRemoteIP(in.get("remoteIP"));
			return this.userProfileService.sendPasswordChangingLinkToEmail(form, principal);
			
		}
		
		return getNegativeResponseMap("ERROR_ACCESS");
	}
	
	@RequestMapping(value = "/resetpassword2",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json")
	public
	@ResponseBody()
	Map sendUserResetPasswordLink2(@RequestBody Map<String, String> in, HttpServletRequest request, Principal principal) throws Exception {
	
		return this.userProfileService.sendPasswordForgorLinkToEmail(in.get("email"), request, principal);
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
