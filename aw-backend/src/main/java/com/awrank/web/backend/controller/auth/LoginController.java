package com.awrank.web.backend.controller.auth;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.backend.exception.UnauthorizedException;
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
import javax.servlet.http.HttpServletResponse;
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
	/*todo: access modifier?*/ AuditorAwareImpl auditorAware;

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap model, Principal principal) {
		model.addAttribute("username", (principal != null) ? principal.getName() : "");
		model.addAttribute("authorities", (principal instanceof AbstractAuthenticationToken) ? ((AbstractAuthenticationToken) principal).getAuthorities() : "");
		return "hello";
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
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

	@RequestMapping(value = "/user/login/google")
	public void loginGoogle(HttpServletRequest request, HttpServletResponse response) {
		String code = request.getParameter("code");
		//TODO GoogleAuthController

	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.GET, headers = "Accept=application/json", produces = "application/json")
	public void logout(HttpServletRequest request) throws UnauthorizedException {
		SecurityContextHolder.clearContext();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		throw UnauthorizedException.getInstance();
	}
}
