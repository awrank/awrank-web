package com.awrank.web.backend.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Olga Korokhina
 * The stub for work with users
 */
@Controller
@RequestMapping(value = "/user")
public class UserController {
	
	@RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String addUser(ModelMap model) {
		return "new user added";
	}

	@RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET})
	public @ResponseBody String deleteUser(ModelMap model) {
		return "user deleted";
	}
}
