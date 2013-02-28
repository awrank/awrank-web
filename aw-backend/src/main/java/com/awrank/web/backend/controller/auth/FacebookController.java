package com.awrank.web.backend.controller.auth;

import com.awrank.web.model.domain.*;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.Principal;
import java.util.List;

/**
 * todo: Class description
 *
 * @author Andrew Stoyaltsev
 */
@Controller
public class FacebookController {

    private static Logger LOG = LoggerFactory.getLogger(FacebookController.class);

    @Autowired
    @Qualifier("entryHistoryServiceImpl")
    private EntryHistoryService entryHistoryService;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;

    @Autowired
    @Qualifier("entryPointServiceImpl")
    private EntryPointService entryPointService;


    /* Network login */
    private final String FACEBOOK_AUTH_URL = "https://www.facebook.com/dialog/oauth";
    private final String FACEBOOK_TOKEN_URL = "https://graph.facebook.com/oauth/access_token?";
    private final String clientId = "124759354366792";
    private final String clientSecret = "a19dff99272c3eb06501619ea4e775a9";
    private final String host = "http://peabody.com.ua:8080/awrank";
    private final String redirectUri = host + "/facebookCallback";
    private String accessToken;

    @RequestMapping(value = "/loginViaFacebook", method = RequestMethod.GET)
    public String loginViaGoogle(ModelMap modelMap) throws IOException {
        modelMap.addAttribute("isError", false);

        StringBuilder queryString = new StringBuilder();
        queryString.append("client_id=").append(clientId).append("&");
        //queryString.append("redirect_uri=").append(URLEncoder.encode(redirectUri, "UTF-8")).append("&");
        queryString.append("redirect_uri=").append(redirectUri).append("&");
        queryString.append("response_type=").append("code");

        String uri = FACEBOOK_AUTH_URL + "?" + queryString;
        System.out.println("Request URI: " + uri);
        return "redirect:" + uri;
    }

    @RequestMapping(value = "/facebookCallback")
    public ModelAndView googleCallback(HttpServletRequest request, Principal principal) throws Exception {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("authStatusCode", "UNKNOWN");
        mav.addObject("authMessage", "Probably none of the conditions were not fulfilled.");


        String authCode = request.getParameter("code");
        if (StringUtils.hasLength(authCode)) {
            mav.addObject("authStatusCode", "POSITIVE");
            mav.addObject("authMessage", "Google returns code: " + authCode);
            LOG.debug("Positive response from Google: code=" + authCode);

            StringBuilder queryString = new StringBuilder();
            queryString.append("client_id=").append(clientId).append("&");
            //queryString.append("redirect_uri=").append(URLEncoder.encode(redirectUri, "UTF-8")).append("&");
            queryString.append("redirect_uri=").append(redirectUri).append("&");
            queryString.append("client_secret=").append(clientSecret).append("&");
            queryString.append("code=").append(authCode);

            HttpClient httpClient = new HttpClient();
            GetMethod getMethod = new GetMethod(FACEBOOK_TOKEN_URL + "?" + queryString);
            int resCode = httpClient.executeMethod(getMethod);
            if (resCode == HttpStatus.SC_OK) {
                String response = getMethod.getResponseBodyAsString();
                mav.addObject("details", "Token request - ok.\n" + response);
                // access_token=AAABxd8zZC20gBAHU6JktVFArUlKub9OLQuIVbNIOvCufrQ31w1wSLryb3UU8aSbyzvXUGEAwwVGkV48yV3kUv9l4WZC7YRIldYJzqwvwZDZD&expires=5114224
                String[] pairs = response.split("&");
                accessToken = pairs[0].split("=")[1];

                String uri = "https://graph.facebook.com/me?access_token=" + accessToken;
                getMethod = new GetMethod(uri);
                resCode = httpClient.executeMethod(getMethod);
                if (resCode == HttpStatus.SC_OK) {
                    JSONObject jsonObject = new JSONObject(getMethod.getResponseBodyAsString());
                    // User class is most applicable for given response
                    User tempUser = new User();
                    tempUser.setEmail(jsonObject.getString("email"));
                    tempUser.setFirstName(jsonObject.getString("first_name"));
                    tempUser.setLastName(jsonObject.getString("last_name"));
                    if (jsonObject.getString("locale").contains("ru")) {
                        tempUser.setLanguage(Language.RU);
                    } else {
                        tempUser.setLanguage(Language.EN);
                    }
                    DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
                    LocalDateTime birthday = LocalDateTime.parse(jsonObject.getString("birthday"), formatter);
                    tempUser.setBirthday(birthday);

                    // find user in db
                    User user = userService.findOneByEmail(tempUser.getEmail());
                    if (user != null) {
                        List<EntryPoint> list = entryPointService.findEntryPointForUserByEntryPointType(user, EntryPointType.FACEBOOK);
                        if (list != null && list.size() > 0) {
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


        return mav;
    }

}
