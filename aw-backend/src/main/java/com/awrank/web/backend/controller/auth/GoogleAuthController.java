package com.awrank.web.backend.controller.auth;

import com.awrank.web.model.domain.*;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserService;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
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
 * This class provides authentication process (request/handling callbacks) for user via
 * <a href="google.com">Google</a> OAuth.
 *
 * @author Andrew Stoyaltsev
 */
@Controller
public class GoogleAuthController {

	private static Logger LOG = LoggerFactory.getLogger(GoogleAuthController.class);

	@Autowired
	@Qualifier("entryHistoryServiceImpl")
	private EntryHistoryService entryHistoryService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;

	@Value("${oauth.google.auth.url}")
	private String socialAuthUrl;

	@Value("${oauth.google.token.url}")
	private String socialTokenUrl;

	//@Value("${oauth.google.clientId}")
	// probably this prop is loaded before profile detection
	private String clientId = "833205848531.apps.googleusercontent.com";

	//@Value("${oauth.google.clientSecret}")
	private String clientSecret = "S56giFrR-y7kDNn2qPFV1hRY";

	// @Value
	private String redirectUri = "http://awrank.com:8080/awrank/googleOAuthCallback";

	@Value("${oauth.google.userinfo.url}")
	private String socialUserInfoUrl;

	private String appUri = "http://awrank.com:8080/awrank";

	private String accessToken;

	@RequestMapping(value = "/loginViaGoogle", method = RequestMethod.GET)
	public String requestAuthCode() throws IOException {
		StringBuilder queryString = new StringBuilder();
		queryString.append("client_id=").append(clientId).append("&");
		queryString.append("scope=").append(URLEncoder.encode(
				"https://www.googleapis.com/auth/userinfo.email" + " " +
				"https://www.googleapis.com/auth/userinfo.profile", "UTF-8")).append("&");
		queryString.append("redirect_uri=").append(URLEncoder.encode(redirectUri, "UTF-8")).append("&");
		// ?do=reg
		queryString.append("response_type=").append("code").append("&");
		queryString.append("state=code");

		String uri = socialAuthUrl + "?" + queryString;
		System.out.println("Request URI: " + uri);
		return "redirect:" + uri;
	}

	@RequestMapping(value = "/googleOAuthCallback")
	public String googleCallback(HttpServletRequest request, Principal principal) throws Exception {
		String state = request.getParameter("state");
		if (StringUtils.hasLength(state)) {

			if (state.equals("code")) { // handle receiving of auth code
				String error = request.getParameter("error");
				if (StringUtils.hasLength(error)) {
					LOG.debug("Negative response from Google: " + error);
					// todo: what json format?
					return error;
				}

				String authCode = request.getParameter("code");
				if (StringUtils.hasLength(authCode)) {
					LOG.debug("Positive response from Google: code=" + authCode);

					LOG.debug("Getting info about user...");
					HttpClient httpClient = new HttpClient();
					PostMethod postMethod = new PostMethod(socialTokenUrl);
					postMethod.addRequestHeader("Host", "accounts.google.com");
					postMethod.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");

					// login / registration ?
					// LoginController::printWelcome  OR  UserController::addUser

					NameValuePair[] params = new NameValuePair[]{
							new NameValuePair("code", authCode),
							new NameValuePair("client_id", clientId),
							new NameValuePair("client_secret", clientSecret),
							//new NameValuePair("redirect_uri", appUri + "/user/add/google"),
							new NameValuePair("redirect_uri", redirectUri),
							new NameValuePair("grant_type", "authorization_code"),
					};
					postMethod.setRequestBody(params);
					int resCode = httpClient.executeMethod(postMethod);
					if (resCode == HttpStatus.SC_OK) {
						String responseJson = postMethod.getResponseBodyAsString();
						LOG.debug("Google response for token request:\n" + responseJson);

						JSONObject jsonObject = new JSONObject(responseJson);
						accessToken = jsonObject.getString("access_token");
						LOG.debug("Google access_token: " + accessToken);

						GetMethod getMethod = new GetMethod(socialUserInfoUrl + accessToken);
						resCode = httpClient.executeMethod(getMethod);
						if (resCode == HttpStatus.SC_OK) {
							jsonObject = new JSONObject(getMethod.getResponseBodyAsString());
							// need to convert give json to format for /user/add request:
							// firstName, lastName, email, password, apiKey?, language
							JSONObject newUser = new JSONObject();
							newUser.put("firstName", jsonObject.getString("given_name"));
							newUser.put("lastName", jsonObject.getString("family_name"));
							newUser.put("email", jsonObject.getString("email"));
							// password?
							// apiKey?
							newUser.put("language", jsonObject.getString("locale"));
							// rest fields from response json

						} else {
							System.out.println("Get userinfo request failed");
						}
					} else {
						System.out.println("Get access_token request failed");
					}
					return null;
				}
			} else {
				System.out.println("Auth code is null");
			}

		}
		return null;
	}

}
