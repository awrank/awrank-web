package com.awrank.web.backend.controller.auth;

import com.awrank.web.backend.authentication.AWRankingUserDetails;
import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserService;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;

/**
 * In this controller we also track login attempts etc. and log to entry_history - do not
 * kill this without making an alternative!
 *
 * @author Andrew Stoyaltsev
 * @author Olga Korokhina
 */
@Controller
public class LoginController extends AbstractController {

    private static Logger LOG = LoggerFactory.getLogger(LoginController.class);

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
    public String printWelcome(ModelMap model, Principal principal, HttpServletRequest request) {

        String name = principal.getName();
        AWRankingUserDetails details =
                (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        List<GrantedAuthority> authorities =
                (List<GrantedAuthority>) ((UsernamePasswordAuthenticationToken) principal).getAuthorities();

        model.addAttribute("username", name);
        model.addAttribute("authorities", authorities);
        //--------- and here we log the success login down ----------

        User user = details.getUser();
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

        List<EntryPoint> list = entryPointService
				.findEntryPointForUserByTypeAndPassword(user, details.getType(), details.getPassword());

        EntryPoint entryPoint;
        if (list.size() > 0) {
            entryPoint = list.get(0);
        } else {
            //---------- if we by some miracle doesn't have an enter point for this user with such a type - create
            entryPoint = new EntryPoint();
            entryPoint.setType(details.getType());//LOGIN
            entryPoint.setUser(user);
            entryPoint.setUid(user.getEmail());
            entryPoint.setPassword(details.getPassword());
            entryPointService.save(entryPoint);
        }

        LocalDateTime time = LocalDateTime.now();

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
    public String login(/*ModelMap model*/) {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(/*ModelMap model*/) {
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

        if (principal != null) {//normally principal is null here and we log bad access in  AWRankingUserDetailsService::loadUserByUsernameAndPassword
            String name = principal.getName();
            AWRankingUserDetails details =
                    (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
            List<GrantedAuthority> authorities =
                    (List<GrantedAuthority>) ((UsernamePasswordAuthenticationToken) principal).getAuthorities();

            model.addAttribute("username", name);
            model.addAttribute("authorities", authorities);
            //--------- and here we log the success login down ----------

            User user = details.getUser();
            LocalDateTime time = LocalDateTime.now();

            Integer acc = user.getAuthorizationFailsCount();
            if (acc == null) acc = 0;
            acc++;
            user.setAuthorizationFailsCount(acc);
            user.setAuthorizationFailsLastDate(time);

            userService.save(user);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            List<EntryPoint> list = entryPointService.findEntryPointForUserByTypeAndPassword(user, details.getType(), details.getPassword());//

            EntryPoint entryPoint;
            if (list.size() > 0) entryPoint = list.get(0);
            else {
                //---------- if we by some miracle don't have an enter point for this user with such a type - create

                entryPoint = new EntryPoint();
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
        return "login";
    }

}
