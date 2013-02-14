package com.awrank.web.backend.controller.user;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;

import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.UserEmailActivation;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.user.UserService;
import com.awrank.web.model.service.user.UserServiceImpl;
import com.awrank.web.model.service.user.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;
import com.awrank.web.model.utils.json.JsonUtils;

/**
 * @author Olga Korokhina
 * The stub for work with users
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	private Map getPositiveResponceMap(){
		
		Map result = new HashMap<String, String>();
		result.put("result", "ok");
		
		return result;
	}
	
	private Map getNegativeResponceMap(String reason){
		
		Map result = new HashMap<String, String>();
		result.put("result", "failure");
		result.put("reason", reason);
		
		return result;
	}

//@Consumes("application/json")
	@RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json", headers = "content-type=application/x-www-form-urlencoded")
	public  @ResponseBody() Map addUser(@ModelAttribute UserRegistrationFormPojo form, HttpServletRequest request) {
		
		if(userService.findByEmail(form.getEmail()).size() > 0) return getNegativeResponceMap("this email already registered in system");
		if(userService.findByAPIKey(form.getApiKey()).size() > 0) return getNegativeResponceMap("this apikey already registered in system");
		
		form.setUserLocalAddr(request.getLocalAddr());
		form.setUserRemoteAddr(request.getRemoteAddr());
		
		try {
			
			userService.register(form);
			
			return getPositiveResponceMap();
		
		} catch (UserNotCreatedException e) {
			
			e.printStackTrace();
			return getNegativeResponceMap(e.getMessage());
		}
		
	}

	//headers = "Accept=application/json"
	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json",headers = "content-type=application/x-www-form-urlencoded")
	//@Consumes("application/json")
	public @ResponseBody Map deleteUser(@RequestBody User user) {
		
		if(userService.findByEmail(user.getEmail()).size() == 0) return getNegativeResponceMap("user with this email not registered in system");
		if(userService.findById(user.getId()).size() == 0) return getNegativeResponceMap("user with this ID not registered in system");
		
		try {
			
			userService.delete(user);
			
			return getPositiveResponceMap();
		
		} catch (UserNotDeletedException e) {
			
			e.printStackTrace();
			return getNegativeResponceMap(e.getMessage());
		}
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/verifyemail/{key}")
	public 
	@ResponseBody
	Map verifyTestEmail(@PathVariable("key") String key, HttpServletRequest request) throws Exception {
		
		//Here we check if such a key exists in db, if yes - fetch user's email and password and build the key with same hasher but with current user IP.
	
		UserEmailActivation record = userService.findEmailVerificationByCode(key);
		
		if(record == null) return getNegativeResponceMap("key not found");
		
		User user = record.getUser();
		
		String new_key = SMTPAuthenticator.getHashed256(user.getEmail()+"."+testactivation_password+"."+request.getLocalAddr() +"."+request.getRemoteAddr());
		
		System.out.println("remote host"+request.getRemoteHost());
		System.out.println("remote adr: "+request.getRemoteAddr());
		System.out.println("X-Forwarded-For: "+request.getHeader("X-Forwarded-For"));
		 
		System.out.println("new_key: "+new_key);
	
		if( new_key.compareToIgnoreCase(key) == 0) return "key " +key+ " verified ok";
		else return "key " +key+ " not verified";
		*/
		return "not implemented in UserController well";
		
		
	}
	
	//------------------- refactor out it not needed ---------------
	
	public void setUserService(UserServiceImpl value){
		
		userService = value;
	}
	
	public UserService getUserService(){
		
		return userService;
	}
}
