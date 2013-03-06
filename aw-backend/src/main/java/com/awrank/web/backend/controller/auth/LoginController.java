package com.awrank.web.backend.controller.auth;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.utils.user.AuditorAwareImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * In this controller we also track login attempts etc. and log to entry_history - do not
 * kill this without making an alternative!
 *
 * @author Andrew Stoyaltsev
 * @author Olga Korokhina
 */
@Controller
public class LoginController extends AbstractController {

	@Autowired
	AuditorAwareImpl auditorAware;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal) {
		model.addAttribute("username", (principal != null) ? principal.getName() : "");
		model.addAttribute("authorities", (principal instanceof AbstractAuthenticationToken) ? ((AbstractAuthenticationToken) principal).getAuthorities() : "");
		return "hello";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
	public
	@ResponseBody
	Map<String, String> login(HttpServletRequest request, @RequestBody Map<String, String> in) {
		String uid = in.get("uid");
		String password = in.get("password");
		// create session
		request.getSession(true);
		auditorAware.setCurrentAuditor(request, uid, password);
		// TODO result
		Map<String, String> map = new HashMap<String, String>();
		return map;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(/*ModelMap model,*/HttpServletRequest request) {
		SecurityContextHolder.clearContext();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		return "logout";
	}

	/**
	 * Unsuccessful attempt - we log it down and increment attempts info for user entry point
	 *
	 * @param model
	 * @param principal
	 * @return
	 */
	@RequestMapping(value = "/loginFailed", method = RequestMethod.GET)
	public String loginFailed(ModelMap model, Principal principal) {
		return "login";
	}

}
