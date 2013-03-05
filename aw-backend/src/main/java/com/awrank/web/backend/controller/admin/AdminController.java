package com.awrank.web.backend.controller.admin;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefaults;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.Diary;
import com.awrank.web.model.domain.DiaryEvent;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.support.DatedAbstractAuditable;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.service.DiaryService;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.StateChangeTokenService;
import com.awrank.web.model.service.UserService;
import com.awrank.web.model.service.email.EmailSenderSendGridImpl;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;

/**
 * @author Olga Korokhina
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends AbstractController {

	@Value("#{emailProps[blocked_xsmtp_header_category]}")
	private String blocked_xsmtp_header_category;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("entryHistoryServiceImpl")
	private EntryHistoryService entryHistoryService;
	
	@Autowired
	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;
	
	@Autowired
	@Qualifier("diaryServiceImpl")
	private DiaryService diaryService;
	
	@Autowired
	EmailSenderSendGridImpl sendGridEmailSender;
	 
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public 
    @ResponseBody()
    String printWelcome(ModelMap model, Principal principal) {	
    	
    		  String name = principal.getName();
    	        List<GrantedAuthority> authorities =
    	                (List<GrantedAuthority>) ((UsernamePasswordAuthenticationToken) principal).getAuthorities();

    	        model.addAttribute("username", name);
    	        model.addAttribute("authorities", authorities);
    	        
    return "hello, admin!";
    }
    
    @RequestMapping(value = "/userlist", method = RequestMethod.GET, produces = "application/json")
    public 
    @ResponseBody()
    List<User> getAll(ModelMap model) {	
    	
    	List<User> allUsers = userService.getAll();
    	
    	model.addAttribute("result", allUsers);
    	
    	        
    return allUsers;
    }
    
    /**
     * List of user with pagination
     * 
     * @param model
     * @param pageable
     * @return
     */
    @RequestMapping(value = "/userlistpage", method = RequestMethod.GET, produces = "application/json")
    public 
    @ResponseBody()
    Page<User> getAllWithPage(ModelMap model, @PageableDefaults(pageNumber = 0, value = 30) Pageable pageable) {	
    	
    	Page<User> allUsers = userService.getPage(pageable);
    	
    	model.addAttribute("result", allUsers);
    	
    	        
    return allUsers;
    }
    
    
    /**
     * Blocking one user by email in form.getEmail()
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(
			value = "/blockuserbyemail",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
	User blockUserByEmail(@ModelAttribute UserRegistrationFormPojo form, Principal principal)
		{
    	
    	if(principal == null) return null;
    	
    	User user = userService.findOneByEmail(form.getEmail());
    	if(user != null) userService.blockUser(user, principal);
		
    	return user;	
	}
    
    /**
     * Blocking one user by id
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(
			value = "/blockuserbyid",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
	User blockUserByID(@ModelAttribute UserRegistrationFormPojo form, Principal principal)
		{
    	
    	if(principal == null) return null;
    	
    	User user = userService.findOne(form.getId());
    	if(user != null) userService.blockUser(user, principal);
		
    	return user;	
	}
    
    /**
     * Finding one user by email in form.getEmail()
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(
			value = "/user",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
	User getUser(@ModelAttribute UserRegistrationFormPojo form, HttpServletRequest request)
		{
    	
    	return userService.findOneByEmail(form.getEmail());	
	}
    
    /**
     * Get users ever logged in from given IP
     * @param form
     * @param model
     * @return
     */
    @RequestMapping(
			value = "/ip",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
    List<User> getUserByIP(@ModelAttribute UserRegistrationFormPojo form, ModelMap model)
			{
    	// here fetch from entry_history and get list of users ever came from given IP
    		ArrayList<User> list = new ArrayList<User>();
    		String ip = String.valueOf(form.getIp());
    		List<EntryHistory> ehlist= entryHistoryService.findByIP(ip);
    		
    		for( EntryHistory e : ehlist ){
    			//Hibernate.initialize(e.getUser());
    			Long id = ((DatedAbstractAuditable) e.getUser()).getId();
    			if(id != null){
	    			User user = userService.findOne(id);
	    			list.add(user);
    			}
    		}
    		
    		model.addAttribute("result", list);
    		return list;
    }
    
    /**
     * Get all entry points for user with given email
     * @param form
     * @param model
     * @return
     */
    @RequestMapping(
			value = "/userentryhistory",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
    List<EntryHistory> getEntryHistoryByUser(@ModelAttribute UserRegistrationFormPojo form, ModelMap model)
			{
    		
    		String email = String.valueOf(form.getEmail());		
    		User user = userService.findOneByEmail(email);
    		List<EntryHistory> ehlist= entryHistoryService.findAllByUser(user);
    		
    		model.addAttribute("result", ehlist);
    		return ehlist;
    }
    
    /**
     * Get all access IPs for user with given email
     * @param form
     * @param model
     * @return
     */
    @RequestMapping(
			value = "/useriphistory",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
    List<String> getIPByUser(@ModelAttribute UserRegistrationFormPojo form, ModelMap model)
			{
    		
    		String email = String.valueOf(form.getEmail());		
    		User user = userService.findOneByEmail(email);
    		List<String> iplist= entryHistoryService.findAllIPByUser(user);
    		
    		model.addAttribute("result", iplist);
    		return iplist;
    }
}
