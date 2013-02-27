package com.awrank.web.backend.controller.auth;

import com.awrank.web.backend.authentication.AWRankingUserDetails;
import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
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
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.ArrayList;
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
        if (list.size() > 0) entryPoint = list.get(0);
        else {
            //---------- if we by some mirricle doesn't have an enter point for this user with such a type - create

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
    public String login(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(ModelMap model) {
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
            AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
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
            List<EntryPoint> list = entryPointService.findEntryPointForUserByEntryPointTypeAndPassword(user, details.getType(), details.getPassword());//

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

    /* Network login */
    @RequestMapping(value = "/loginViaGoogle", method = RequestMethod.GET)
    public String loginViaGoogle(ModelMap modelMap) throws IOException {
        modelMap.addAttribute("isError", false);

        final String AUTH_ENDPOINT_URL = "https://accounts.google.com/o/oauth2/auth";
        final String clientId = "567712796156-lh9rc61kk5qsllng54sk314sui0tls09.apps.googleusercontent.com";
        final String clientSecret = "IML_o6lOCHm6ysnbQi_VQsL9";
        final String redirectUrl = "http://awrank.com:8080/awrank/googleAuthSuccess";

        StringBuilder requestParams = new StringBuilder();
        requestParams.append("client_id=").append(clientId).append("&");
        requestParams.append("scope=").append(URLEncoder.encode(
                "https://www.googleapis.com/auth/userinfo.email" +
                        " " +
                        "https://www.googleapis.com/auth/userinfo.profile", "UTF-8"))
                .append("&");
        requestParams.append("redirect_uri=").append(URLEncoder.encode(redirectUrl, "UTF-8")).append("&");
        requestParams.append("response_type=").append("code");//.append("&");

        HttpClient httpClient = new HttpClient();
        //String url = URLEncoder.encode(requestParams.toString(), "UTF-8");
        HttpMethod method = new GetMethod(AUTH_ENDPOINT_URL + "?" + requestParams);
        int code = httpClient.executeMethod(method);
        if (code == HttpStatus.SC_OK) {
            System.out.println("Google request ok!");
            modelMap.addAttribute("isError", true);
            modelMap.addAttribute("errorMessage", "Google request ok!");
            //return "forward:/index.html";
        } else {
            modelMap.addAttribute("isError", true);
            modelMap.addAttribute("errorMessage", "Http code = " + code);
            //return "login";
        }
        return "login";
    }

    @RequestMapping(value = "/googleAuthSuccess")
    public ModelAndView googleAuthSuccess(HttpServletRequest request) throws Exception {
        System.out.println(request);

        return null;
    }

    @RequestMapping(value = "/oauth2callback")
    public ModelAndView googleCallback(HttpServletRequest request) throws Exception {
        System.out.println(request);

        return null;
    }




    /* Social Auth */
    @Autowired
    private SocialAuthTemplate socialAuthTemplate;

    @RequestMapping(value = "/socialAuthSuccess")
    public ModelAndView socialAuthSuccess(final HttpServletRequest request) throws Exception {
        System.out.println(request);

        return null;
    }

    @RequestMapping(value = "/socialAuthFailed")
    public String socialAuthFailed() {
        return null;
    }

}
