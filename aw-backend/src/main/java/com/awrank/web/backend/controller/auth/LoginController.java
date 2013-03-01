package com.awrank.web.backend.controller.auth;

import com.awrank.web.backend.authentication.AWRankingUserDetails;
import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private final String GOOGLE_AUTH_URL = "https://accounts.google.com/o/oauth2/auth";
    private final String GOOGLE_TOKEN_URL = "https://accounts.google.com/o/oauth2/token";
    private final String clientId = "567712796156-lh9rc61kk5qsllng54sk314sui0tls09.apps.googleusercontent.com";
    private final String clientSecret = "IML_o6lOCHm6ysnbQi_VQsL9";
    private final String host = "http://peabody.com.ua:8080/awrank";
    private final String redirectUri = host + "/googleCallback";
    private String accessToken;

    @RequestMapping(value = "/loginViaGoogle", method = RequestMethod.GET)
    public String loginViaGoogle(ModelMap modelMap) throws IOException {
        modelMap.addAttribute("isError", false);
        StringBuilder queryString = new StringBuilder();
        queryString.append("client_id=").append(clientId).append("&");
        queryString.append("scope=").append(URLEncoder.encode(
                "https://www.googleapis.com/auth/userinfo.email" + " " + "https://www.googleapis.com/auth/userinfo.profile", "UTF-8"))
                .append("&");
        queryString.append("redirect_uri=").append(URLEncoder.encode(redirectUri, "UTF-8")).append("&");
        queryString.append("response_type=").append("code").append("&");
        queryString.append("state=code");

        String uri = GOOGLE_AUTH_URL + "?" + queryString;
        System.out.println("Request URI: " + uri);
        return "redirect:" + uri;
    }

    @RequestMapping(value = "/googleCallback")
    public ModelAndView googleCallback(HttpServletRequest request, Principal principal) throws Exception {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("authStatusCode", "UNKNOWN");
        mav.addObject("authMessage", "Probably none of the conditions were not fulfilled.");

        String state = request.getParameter("state");
        if (StringUtils.hasLength(state)) {

            if (state.equals("code")) { // handle receiving of auth code
                String error = request.getParameter("error");
                if (StringUtils.hasLength(error)) {
                    mav.addObject("authStatusCode", "NEGATIVE");
                    mav.addObject("authMessage", error);
                    LOG.debug("Negative response from Google: " + error);
                    return mav;
                }

                String authCode = request.getParameter("code");
                if (StringUtils.hasLength(authCode)) {
                    mav.addObject("authStatusCode", "POSITIVE");
                    mav.addObject("authMessage", "Google returns code: " + authCode);
                    LOG.debug("Positive response from Google: code=" + authCode);

                    LOG.debug("Getting info about user...");

                    HttpClient httpClient = new HttpClient();
                    PostMethod postMethod = new PostMethod(GOOGLE_TOKEN_URL);
                    postMethod.addRequestHeader("Host", "accounts.google.com");
                    postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");

                    NameValuePair[] params = new NameValuePair[]{
                            new NameValuePair("code", authCode),
                            new NameValuePair("client_id", clientId),
                            new NameValuePair("client_secret", clientSecret),
                            new NameValuePair("redirect_uri", redirectUri),
                            new NameValuePair("grant_type", "authorization_code"),
                    };
                    postMethod.setRequestBody(params);
                    int resCode = httpClient.executeMethod(postMethod);
                    if (resCode == HttpStatus.SC_OK) {
                        String responseJson = postMethod.getResponseBodyAsString();
                        mav.addObject("details", "Token request - ok.\n" + responseJson);

                        JSONObject jsonObject = new JSONObject(responseJson);
                        accessToken = jsonObject.getString("access_token");
                        String uri = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + accessToken;
                        GetMethod getMethod = new GetMethod(uri);
                        resCode = httpClient.executeMethod(getMethod);
                        if (resCode == HttpStatus.SC_OK) {
                            jsonObject = new JSONObject(getMethod.getResponseBodyAsString());
                            // User class is most applicable for given response
                            User tempUser = new User();
                            tempUser.setEmail(jsonObject.getString("email"));
                            tempUser.setFirstName(jsonObject.getString("given_name"));
                            tempUser.setLastName(jsonObject.getString("family_name"));
                            if (jsonObject.getString("locale").equals("ru")) {
                                tempUser.setLanguage(Language.RU);
                            } else {
                                tempUser.setLanguage(Language.EN);
                            }
                            DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
                            LocalDateTime birthday = LocalDateTime.parse(jsonObject.getString("birthday"), formatter);
                            tempUser.setBirthday(birthday);

                            // find user in db
                            User user = userService.findOneByEmail(tempUser.getEmail());
                            if (user != null) {
                                List<EntryPoint> list = entryPointService.findEntryPointForUserByEntryPointType(user, EntryPointType.GOOGLE);
                                if (list != null && list.size()>0) {
                                    EntryPoint entryPoint = list.get(0);

                                    EntryHistory entryHistory = new EntryHistory();
                                    entryHistory.setUser(tempUser);
                                    entryHistory.setSuccess(true);
                                    entryHistory.setSessionId(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getSessionId());
                                    entryHistory.setEntryPoint(entryPoint);
                                    entryHistory.setSigninDate(LocalDateTime.now());
                                    entryHistory.setIpAddress(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getRemoteAddress());
                                    entryHistoryService.save(entryHistory);
                                    mav.setViewName("redirect:/index.html");
                                } else {
                                    mav.addObject("details", "Sorry, we can't recognize you. Probably you're not registered yet.");
                                }
                            } else {
                                mav.addObject("details", "User not found by email: " + tempUser.getEmail());
                                // for testing: redirect anyway even if user is not found
                                //mav.setViewName("redirect:/index.html");
                            }
                        } else {
                            mav.addObject("details", "Could not get userinfo");
                        }
                    } else {
                        mav.addObject("details", "Token request - nok");
                    }

                    return mav;
                }
            } else if (state.equals("token")) {
                System.out.println(request.getParameterNames());
            }

        }


        return mav;
    }

    //@Value("#{emailProps[mail_sg_smtp_server_host]}")

}
