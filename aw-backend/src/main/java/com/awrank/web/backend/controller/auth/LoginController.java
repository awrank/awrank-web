package com.awrank.web.backend.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

/**
 * @author Andrew Stoyaltsev
 */
@Controller
public class LoginController {

	@RequestMapping(value = "/loggedIn"/*, method = RequestMethod.POST*/)
	public String loginSuccess(ModelMap modelMap, Principal principal) {

		System.out.println();
		return "/user/home";
	}

}
