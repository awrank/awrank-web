package com.awrank.web.backend.controller.admin;

import java.security.Principal;
import java.util.List;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.awrank.web.backend.controller.AbstractController;

/**
 * @author Olga Korokhina
 *
 */
@Controller
@RequestMapping(value = "/admin")
public class AdminController extends AbstractController {

    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, Principal principal) {	
    	
    		  String name = principal.getName();
    	        List<GrantedAuthority> authorities =
    	                (List<GrantedAuthority>) ((UsernamePasswordAuthenticationToken) principal).getAuthorities();

    	        model.addAttribute("username", name);
    	        model.addAttribute("authorities", authorities);
    	        
    return "hello, admin!";
    }
}
