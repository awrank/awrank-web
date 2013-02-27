package com.awrank.web.backend.controller.rest;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awrank.web.backend.authentication.AWRankingGrantedAuthority;
import com.awrank.web.backend.authentication.AWRankingUserDetails;
import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.support.DatedAbstractAuditable;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserEmailActivationService;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;

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
	private UserEmailActivationService userEmailActivationService;
	
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
		 	//((DatedAbstractAuditable) user).getId();
			Page<EntryHistory> allEntryHistory = entryHistoryService.getPageByUser(user, pageable);
	    	
	    	model.addAttribute("result", allEntryHistory.getContent());
	    	        
	    	return allEntryHistory;
		}
	 
	 /**
	  *  Request password change. Think about if we shall also see what is in Principal and if user logged in and has no 
	  *  ROLE_ADMIN and specified another email reject this request?
	  * @param form
	  * @param model
	  * @param principal
	  * @return
	  */
	 @RequestMapping(value = "/resetpassword",
				method = {RequestMethod.POST, RequestMethod.GET},
				produces = "application/json")
		public
		@ResponseBody()
	 	String sendUserResetPasswordLink(@ModelAttribute UserRegistrationFormPojo form, ModelMap model, Principal principal)
		{
		//---------- check if we are logged in user ----------
		 if(principal != null){
		 	AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();	       
		 	User user = details.getUser();
		 	ArrayList<AWRankingGrantedAuthority> list = (ArrayList<AWRankingGrantedAuthority>) details.getAuthorities();
		 	Boolean isAdmin = false;
		 	
		 	for(AWRankingGrantedAuthority auth : list) {
		 	
		 		if(auth.getAuthority() == String.valueOf(Role.ROLE_ADMIN)) isAdmin = true;
		 	
		 	}
		 	
		 	String email = details.getUsername();//actually this is an email
		 	
		 	if(!isAdmin && (email.compareTo(form.getEmail()) != 0)) {
		 		
		 		return "You are not autorized to change password for user with given email";
		 		}
		 	}
		 	  
		 //-------- not logged in or an admin so send the link -------------------	
		 
			Map<String, Object> params = new HashMap<String, Object>();

			params.put("localAddr", form.getUserLocalAddr());
			params.put("remoteAddr", form.getUserRemoteAddr());
			params.put("testactivation_email", form.getEmail());
			
			try {
				userEmailActivationService.send(params);
			} catch (UserActivationEmailNotSetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
		 
	    	return "Link was sent to your email";
		}
}
