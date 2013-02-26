package com.awrank.web.backend.controller.rest;

import java.security.Principal;
import java.util.List;

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

import com.awrank.web.backend.authentication.AWRankingUserDetails;
import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.support.DatedAbstractAuditable;
import com.awrank.web.model.service.EntryHistoryService;
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
	
	 @RequestMapping(value = "/accesshistory",
				method = {RequestMethod.POST, RequestMethod.GET},
				produces = "application/json")
		public
		@ResponseBody()
	 	Page<EntryHistory> getUserLoginHistory(ModelMap model, @PageableDefaults(pageNumber = 0, value = 300) Pageable pageable, Principal principal)
		{
		
		 	AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();	       
		 	User user = details.getUser(); 
		 	((DatedAbstractAuditable) user).getId();
			Page<EntryHistory> allEntryHistory = entryHistoryService.getPageByUser(user, pageable);
	    	
	    	model.addAttribute("result", allEntryHistory.getContent());
	    	        
	    	return allEntryHistory;
		}
}
