package com.awrank.web.backend.controller.auth;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.spring.bean.SocialAuthTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
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

	/* Social Auth */
	@Autowired
	private SocialAuthTemplate socialAuthTemplate;

	@RequestMapping(value = "/authSuccess")
	public ModelAndView authSuccessPage(final HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();
		List<Contact> contactsList = new ArrayList<Contact>();
		SocialAuthManager manager = socialAuthTemplate.getSocialAuthManager();
		if (manager != null) {
			AuthProvider provider = manager.getCurrentAuthProvider();
			if (provider != null) {
				contactsList = provider.getContactList();
				if (contactsList != null && contactsList.size() > 0) {
					for (Contact p : contactsList) {
						if (!StringUtils.hasLength(p.getFirstName())
								&& !StringUtils.hasLength(p.getLastName())) {
							p.setFirstName(p.getDisplayName());
						}
					}
				}
				mv.addObject("profile", provider.getUserProfile());
				mv.addObject("contacts", contactsList);
			} else {
				System.out.println("Provider is null!");
			}
		} else {
			System.out.println("Manager is null!");
		}

		mv.setViewName("authSuccess");
		return mv;
	}

	@RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
	public ModelAndView statusSuccessPage(final HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		SocialAuthManager manager = socialAuthTemplate.getSocialAuthManager();
		if (manager != null) {
			AuthProvider provider = manager.getCurrentAuthProvider();
			if (provider != null) {
				String statusMsg = request.getParameter("statusMessage");
				try {
					provider.updateStatus(statusMsg);
					mv.addObject("Message", "Status Updated successfully");
				} catch (SocialAuthException e) {
					mv.addObject("Message", e.getMessage());
					e.printStackTrace();
				}
			} else {
				mv.addObject("Message", "AuthProvider is null. Configuration has problems!");
			}

		} else {
			mv.addObject("Message", "SocialAuthManager is null. Configuration has problems!");
		}

		mv.setViewName("statusSuccess");
		return mv;
	}

//	@RequestMapping(value = "/socialauth")
//	public void socialAuthCallback() {
//		System.out.println("socialauth-callback");
//	}

	@RequestMapping(value = "/accessDenied")
	public String accessDenied() {
		return "accessDenied";
	}

}
