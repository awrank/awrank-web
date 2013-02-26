package com.awrank.web.backend.controller.admin;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
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

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.support.DatedAbstractAuditable;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.UserService;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;

/**
 * @author Olga Korokhina
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends AbstractController {
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("entryHistoryServiceImpl")
	private EntryHistoryService entryHistoryService;
	
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
    
    @RequestMapping(value = "/userlistpage", method = RequestMethod.GET, produces = "application/json")
    public 
    @ResponseBody()
    Page<User> getAllWithPage(ModelMap model, @PageableDefaults(pageNumber = 0, value = 30) Pageable pageable) {	
    	
    	Page<User> allUsers = userService.getPage(pageable);
    	
    	model.addAttribute("result", allUsers);
    	
    	        
    return allUsers;
    }
    
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
    
    @RequestMapping(
			value = "/ip",
			method = {RequestMethod.POST, RequestMethod.GET},
			produces = "application/json",
			headers = "content-type=application/x-www-form-urlencoded")
	public
	@ResponseBody()
    Object[] getUserByIP(@ModelAttribute UserRegistrationFormPojo form, ModelMap model)
			{
    	// here fetch from entry_history and get list of users ever came from given IP
    		ArrayList<User> list = new ArrayList<User>();
    		String ip = String.valueOf(form.getIp());
    		List<EntryHistory> ehlist= entryHistoryService.findByIP(ip);
    		
    		for( EntryHistory e : ehlist ){
    			
    			Hibernate.initialize(e.getUser());
    			Long id = ((DatedAbstractAuditable) e.getUser()).getId();
    			User user = userService.findOne(id);
    			list.add(user);
    		}
    		
    		model.addAttribute("result", list.toArray());
    		return list.toArray();
				
    }
}
