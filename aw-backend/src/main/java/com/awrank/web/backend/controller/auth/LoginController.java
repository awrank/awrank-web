package com.awrank.web.backend.controller.auth;

import org.brickred.socialauth.AuthProvider;
import org.brickred.socialauth.Contact;
import org.brickred.socialauth.SocialAuthManager;
import org.brickred.socialauth.exception.SocialAuthException;
import org.brickred.socialauth.spring.bean.SocialAuthTemplate;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import com.awrank.web.backend.authentication.AWRankingGrantedAuthority;
import com.awrank.web.backend.authentication.AWRankingUserDetails;

/**
 * In this controller we also track login attempts etc. and log to entry_history - do not 
 * kill this without making an alternative!
 * 
 * @author Andrew Stoyaltsev
 * @author Olga Korokhina
 */
@Controller
//@RequestMapping(value = "/auth")
public class LoginController extends AbstractController {

	@Autowired
	@Qualifier("entryHistoryServiceImpl")
	private EntryHistoryService entryHistoryService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;
	
	@Autowired
	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;
	
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, Principal principal) {

        String name = principal.getName();
        AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        List<GrantedAuthority> authorities =
                (List<GrantedAuthority>) ((UsernamePasswordAuthenticationToken) principal).getAuthorities();

        model.addAttribute("username", name);
        model.addAttribute("authorities", authorities);
        //--------- and here we log the success login down ----------
       
        User user = details.getUser(); 
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        
        List<EntryPoint> list = entryPointService.findEntryPointForUserByEntryPointTypeAndPassword(user, details.getType(), details.getPassword());//
       
        EntryPoint entryPoint;
        if(list.size() > 0) entryPoint = list.get(0);
        else {
        //---------- if we by some mirricle doesn't have an enter point for this user with such a type - create	
        	
        	entryPoint =  new EntryPoint();
        	entryPoint.setType(details.getType());//LOGIN
        	entryPoint.setUser(user);
        	entryPoint.setPassword(details.getPassword());
        	
        	entryPointService.save(entryPoint);
        }
        
        LocalDateTime time  = LocalDateTime.now();
        
        EntryHistory entryHistory = new EntryHistory();
		entryHistory.setUser(user);
		entryHistory.setSuccess(true);
		entryHistory.setSessionId(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getSessionId());
		entryHistory.setEntryPoint(entryPoint);
		entryHistory.setSigninDate(time);
		entryHistory.setIpAddress(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getRemoteAddress());
		
		entryHistoryService.save(entryHistory);
		
        return "hello";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
        return "logout";
    }
    
    /**
     * Unsuccessful attempt - we log it down and increment attempts info for user entry point 
     * @param model
     * @param principal
     * @return
     */
    @RequestMapping(value = "/loginFailed", method = RequestMethod.GET)
    public String loginFailed(ModelMap model, Principal principal) {
    	
    	if(principal != null){//normally principal is null here and we log bad access in  AWRankingUserDetailsService::loadUserByUsernameAndPassword
	    	 String name = principal.getName();
	         AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
	         List<GrantedAuthority> authorities =
	                 (List<GrantedAuthority>) ((UsernamePasswordAuthenticationToken) principal).getAuthorities();
	
	         model.addAttribute("username", name);
	         model.addAttribute("authorities", authorities);
	         //--------- and here we log the success login down ----------
	        
	         User user = details.getUser(); 
	         LocalDateTime time  = LocalDateTime.now();
	         
	         Integer acc = user.getAuthorizationFailsCount();
	         if(acc == null) acc = 0;
	         acc++;
	         user.setAuthorizationFailsCount(acc);
	         user.setAuthorizationFailsLastDate(time);
	         
	         userService.save(user);
	         
	         ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
	         List<EntryPoint> list = entryPointService.findEntryPointForUserByEntryPointTypeAndPassword(user, details.getType(), details.getPassword());//
	        
	         EntryPoint entryPoint;
	         if(list.size() > 0) entryPoint = list.get(0);
	         else {
	         //---------- if we by some mirricle doesn't have an enter point for this user with such a type - create	
	         	
	         	entryPoint =  new EntryPoint();
	         	entryPoint.setType(details.getType());//LOGIN
	         	entryPoint.setUser(user);
	         	entryPoint.setPassword(details.getPassword());
	      	
	         }
	         
	         entryPointService.save(entryPoint);
	         
	         EntryHistory entryHistory = new EntryHistory();
	 		entryHistory.setUser(user);
	 		entryHistory.setSuccess(true);
	 		entryHistory.setSessionId(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getSessionId());
	 		entryHistory.setEntryPoint(entryPoint);
	 		entryHistory.setSigninDate(time);
	 		entryHistory.setIpAddress(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getRemoteAddress());
	 		
	 		entryHistoryService.save(entryHistory);
	    	
	        model.addAttribute("error", "true");
    	}
        return "login Failed";
    }

//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logout(ModelMap model) {
//        SecurityContextHolder.clearContext();
//
//        return "login";
//    }

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
