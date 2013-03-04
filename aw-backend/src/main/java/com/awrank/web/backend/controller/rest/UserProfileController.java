package com.awrank.web.backend.controller.rest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awrank.web.backend.authentication.AWRankingGrantedAuthority;
import com.awrank.web.backend.authentication.AWRankingUserDetails;
import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.StateChangeToken;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.support.DatedAbstractAuditable;
import com.awrank.web.model.service.UserPasswordChangingService;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.enums.StateChangeTokenType;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserService;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.service.impl.pojos.UserNewPasswordFormPojo;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;

/**
 * Controller exposes basic methods to work with user profile (with user access particular)
 *  through REST.
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
	
	/**
	 *  Get the access history for currently logged in user, used Principal as a param 
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
	 	Page<EntryHistory> getUserLoginHistory(ModelMap model, @PageableDefaults(pageNumber = 0, value = 100) Pageable pageable, Principal principal)
		{
		
		 	AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();	       
		 	User user = details.getUser(); 
		 	((DatedAbstractAuditable) user).getId();
		 	user.getFirstName();
			Page<EntryHistory> allEntryHistory = entryHistoryService.getPageByUser(user, pageable);
	    	
	    	model.addAttribute("result", allEntryHistory.getContent());
	    	        
	    	return allEntryHistory;
		}
	 
	 	
	 	
	 	 @RequestMapping(value = "/newpassword",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json")
	public
	@ResponseBody()
	Map setUserNewPassword(@ModelAttribute UserNewPasswordFormPojo form, ModelMap model, HttpServletRequest request) throws Exception
	{
	 		
	 		 return getPositiveResponseMap("was in setUserNewPassword "+form.getPassword()+" "+form.getPasswordConfirm());
	 		
	}
	 	
	 /**
	  * Handler for password changing - if token found redirect to passwrd changing form
	  * @param key
	  * @param request
	  * @return
	  * @throws Exception
	  */
	 @RequestMapping(method = RequestMethod.GET, value = "/changepassword/{key}")
		public
		String verifyTestEmail(@PathVariable("key") String key, HttpServletRequest request, ModelMap modelMap) throws Exception {

		 Boolean response = userPasswordChangingService.verify(key, request);

			if (response == false){
				
				modelMap.addAttribute("params", getNegativeResponseMap("not found password changing token"));
				return "403";
			}
			else{
				//TODO: put here found old password and/or user?
				modelMap.addAttribute("params", getPositiveResponseMap());
				
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
	  *  Request password change. Think about if we shall also see what is in Principal and if user logged in and has no 
	  *  ROLE_ADMIN and specified another email reject this request?
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
	 	Map sendUserResetPasswordLink(@ModelAttribute UserRegistrationFormPojo form, ModelMap model, Principal principal, HttpServletRequest request) throws Exception
		{
		//---------- check if we are logged in user ----------
		 if(principal != null){
		 	AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();	       
		 	User user = details.getUser();
		 	ArrayList<AWRankingGrantedAuthority> list = (ArrayList<AWRankingGrantedAuthority>) details.getAuthorities();
		 	
		 	Boolean isAdmin = false;
			Boolean isUserEmailVerified = false;
		 	
			for(AWRankingGrantedAuthority auth : list) {
		 	
		 		if(auth.getAuthority() == String.valueOf(Role.ROLE_ADMIN)) isAdmin = true;
		 		if(auth.getAuthority() == String.valueOf(Role.ROLE_USER_VERIFIED)) isUserEmailVerified = true;
		 	
		 	}
		 	
		 	String email = details.getUsername();//actually this is an email
		 	
		 	if(!isAdmin){
		 		
		 		if ((email.compareTo(form.getEmail()) != 0) || !isUserEmailVerified) {//if user is not an admin, enter not his email or email is his but not verified
		 		
		 				return getNegativeResponseMap("You are not autorized to change password for user with given email"); 
		 			}
		 		}
		 	}
		 	  
		 //-------- not logged in or an admin so send the link -------------------	
		 
		 	User user = userService.findOneByEmail(form.getEmail());
		 	String testactivation_password = entryPointService.findPasswordForUserByType(user, EntryPointType.EMAIL);//current password
		 	
		 	if(testactivation_password == null) return getNegativeResponseMap("No current password");
		 	
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
			
	    	return getPositiveResponseMap("password changing link sent to "+form.getEmail());
		}
}
