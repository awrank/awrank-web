package com.awrank.web.backend.controller.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.List;

/**
 * @author Andrew Stoyaltsev
 */
@Controller
//@RequestMapping(value = "/auth")
public class LoginController {

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal) {

		String name = principal.getName();
		List<GrantedAuthority> authorities =
				(List<GrantedAuthority>) ((UsernamePasswordAuthenticationToken) principal).getAuthorities();

		model.addAttribute("username", name);
		model.addAttribute("authorities", authorities);
		// define user role.
		return "hello";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(ModelMap model) {
		return "login";
	}

	@RequestMapping(value = "/loginFailed", method = RequestMethod.GET)
	public String loginFailed(ModelMap model) {
		model.addAttribute("error", "true");
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model) {
		SecurityContextHolder.clearContext();

		return "login";
	}


}
