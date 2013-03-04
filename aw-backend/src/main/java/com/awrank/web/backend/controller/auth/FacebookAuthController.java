package com.awrank.web.backend.controller.auth;

import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.Language;
import com.awrank.web.model.service.SocialAuthService;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This class provides authentication process (request/handling callbacks) for user via
 * <a href="facebook.com">Facebook</a> OAuth.
 *
 * @author Andrew Stoyaltsev
 */
@Controller
public class FacebookAuthController {

	/* TODO: refactor together with GoogleController */
	/* authVia... and callback methods could be folded to one. */

	private static Logger LOG = LoggerFactory.getLogger(FacebookAuthController.class);

	@Autowired
	private SocialAuthService socialAuthService;

	@Value("${oauth.facebook.auth.url}")
	private String socialAuthUrl;

	@Value("${oauth.facebook.token.url}")
	private String socialTokenUrl;

	//@Value("${oauth.facebook.appId}")
	private String clientId = "347948558644172";

	//@Value("${oauth.facebook.appSecret}")
	private String clientSecret = "270be44d06652edad696a58438dfbd5a";

	//@Value("${oauth.facebook.redirect.uri}")
	private String redirectUri = "http://awrank.com:8080/awrank/facebookOAuthCallback";

	@Value("${oauth.facebook.userinfo.url}")
	private String socialUserInfoUrl;

	private String authAction;

	@RequestMapping(value = "/auth/facebook/{action}", method = RequestMethod.GET)
	public String authViaFacebook(@PathVariable(value = "action") String action) throws IOException {
		if (StringUtils.hasLength(action)) {
			this.authAction = action;
			return "redirect:" + getRequestAuthCodeURI();
		} else {
			System.out.println("Error: Google Auth action not specified!");
		}
		return "login";
	}

	/**
	 * @return URI to request authentication code from Facebook.
	 * @throws IOException
	 */
	private String getRequestAuthCodeURI() throws IOException {
		StringBuilder queryString = new StringBuilder();
		queryString.append("client_id=").append(clientId).append("&");
		queryString.append("redirect_uri=").append(redirectUri).append("&");
		queryString.append("response_type=").append("code");
		String uri = socialAuthUrl + "?" + queryString;
		LOG.debug("Facebook request auth code URI: " + uri);
		return uri;
	}

	@RequestMapping(value = "/facebookOAuthCallback")
	public String facebookCallback(HttpServletRequest request) throws Exception {
		String authCode = request.getParameter("code");
		if (StringUtils.hasLength(authCode)) {
			LOG.debug("Facebook response: auth code=" + authCode);

			String accessToken = requestAccessToken(authCode);
			LOG.debug("Facebook access_token: " + accessToken);

			if (StringUtils.hasLength(accessToken)) {
				UserRegistrationFormPojo userInfo = requestUserInfo(accessToken);
				if (this.authAction.equals("login")) {
					socialAuthService.login(userInfo, request);
					// need check if login ok? then redirect
					return "redirect:index.html";
				} else if (this.authAction.equals("register")) {
					// need check if registration ok? then redirect
					socialAuthService.register(userInfo, request);
					return "redirect:index.html"; // or profile page?
				}
			} else {
				LOG.warn("Access token is null");
			}
		}
		return null;
	}

	/**
	 * @param authCode
	 * @return
	 * @throws IOException
	 */
	private String requestAccessToken(String authCode) throws IOException {
		LOG.debug("Request Facebook for access token...");
		String token = null;
		StringBuilder queryString = new StringBuilder();
		queryString.append("client_id=").append(clientId).append("&");
		queryString.append("redirect_uri=").append(redirectUri).append("&");
		queryString.append("client_secret=").append(clientSecret).append("&");
		queryString.append("code=").append(authCode);

		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(socialTokenUrl + "?" + queryString);
		int resCode = httpClient.executeMethod(getMethod);
		if (resCode == HttpStatus.SC_OK) {
			String response = getMethod.getResponseBodyAsString();
			String[] pairs = response.split("&");
			token = pairs[0].split("=")[1];
		} else {
			LOG.warn("Facebook request for access_token request failed!");
		}
		return token;
	}

	/**
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	private UserRegistrationFormPojo requestUserInfo(String accessToken) throws IOException, JSONException {
		UserRegistrationFormPojo userInfo = new UserRegistrationFormPojo();
		LOG.debug("Request Facebook for user info...");

		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(socialUserInfoUrl + accessToken);
		int resCode = httpClient.executeMethod(getMethod);
		if (resCode == HttpStatus.SC_OK) {
			JSONObject jsonObject = new JSONObject(getMethod.getResponseBodyAsString());
			userInfo.setFirstName(jsonObject.optString("first_name"));
			userInfo.setLastName(jsonObject.optString("last_name"));
			userInfo.setEmail(jsonObject.optString("email"));
			if (StringUtils.hasLength(jsonObject.optString("locale"))) {
				if (jsonObject.optString("locale").contains("ru")) {
					userInfo.setLanguage(Language.RU);
				} else {
					userInfo.setLanguage(Language.EN);
				}
			}
			userInfo.setNetworkType(EntryPointType.FACEBOOK);
			userInfo.setNetworkUID(jsonObject.optString("id"));
			userInfo.setEmailVerified(jsonObject.optBoolean("verified"));
			if (StringUtils.hasLength(jsonObject.optString("birthday"))) {
				// todo: move out date format to constants
				DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
				LocalDateTime birthday = LocalDateTime.parse(jsonObject.optString("birthday"), formatter);
				userInfo.setBirthday(birthday);
			}
		} else {
			LOG.warn("Get Facebook userinfo request failed");
		}
		return userInfo;
	}
}
