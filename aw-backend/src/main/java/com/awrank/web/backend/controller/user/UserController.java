package com.awrank.web.backend.controller.user;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;

import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.user.UserService;
import com.awrank.web.model.service.user.UserServiceImpl;
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
	public  @ResponseBody() Map addUser(@ModelAttribute User user) {
		
		if(userService.findByEmail(user.getEmail()).size() > 0) return getNegativeResponceMap("this email already registered in system");
		if(userService.findByAPIKey(user.getApiKey()).size() > 0) return getNegativeResponceMap("this apikey already registered in system");
			
		try {
			
			userService.add(user);
			
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
	
	
	//------------------- refactor out it not needed ---------------
	
	public void setUserService(UserServiceImpl value){
		
		userService = value;
	}
	
	public UserService getUserService(){
		
		return userService;
	}
}