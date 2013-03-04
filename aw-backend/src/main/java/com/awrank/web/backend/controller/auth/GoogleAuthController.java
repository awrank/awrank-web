package com.awrank.web.backend.controller.auth;

import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.Language;
import com.awrank.web.model.service.SocialAuthService;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
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
import java.net.URLEncoder;

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
	private SocialAuthService socialAuthService;

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

	private String authAction = null;

	@RequestMapping(value = "/auth/google/{action}", method = RequestMethod.GET)
	public String authViaGoogle(@PathVariable(value = "action") String action) throws IOException {
		if (StringUtils.hasLength(action)) {
			this.authAction = action;
			return "redirect:" + getRequestAuthCodeURI();
		} else {
			System.out.println("Error: Google Auth action not specified!");
		}
		return "login";
	}

	/**
	 * @return
	 * @throws IOException
	 */
	private String getRequestAuthCodeURI() throws IOException {
		StringBuilder queryString = new StringBuilder();
		queryString.append("client_id=").append(clientId).append("&");
		queryString.append("scope=").append(URLEncoder.encode(
				"https://www.googleapis.com/auth/userinfo.email" + " " +
						"https://www.googleapis.com/auth/userinfo.profile", "UTF-8")).append("&");
		queryString.append("redirect_uri=").append(URLEncoder.encode(redirectUri, "UTF-8")).append("&");
		queryString.append("response_type=").append("code").append("&");
		queryString.append("state=code");
		String uri = socialAuthUrl + "?" + queryString;
		LOG.debug("Google request auth code URI: " + uri);
		return uri;
	}

	@RequestMapping(value = "/googleOAuthCallback")
	public String googleCallback(HttpServletRequest request) throws Exception {
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
					LOG.debug("Google response: auth code=" + authCode);

					String accessToken = requestAccessToken(authCode);
					LOG.debug("Google access_token: " + accessToken);

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
				} else {
					LOG.warn("Auth code is null");
				}
			} else {
				LOG.warn("Custom 'state' param does not correspond initial request value! state=" + state);
			}
		}
		return null;
	}

	/**
	 *
	 * @param authCode
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	private String requestAccessToken(String authCode) throws IOException, JSONException {
		String token = null;
		LOG.debug("Request Google for access token...");
		HttpClient httpClient = new HttpClient();
		PostMethod postMethod = new PostMethod(socialTokenUrl);
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
			LOG.debug("Google response for token request:\n" + responseJson);

			JSONObject jsonObject = new JSONObject(responseJson);
			token = jsonObject.optString("access_token");
			LOG.debug("Google access_token: " + token);
		} else {
			LOG.warn("Google request for access_token request failed!");
		}
		return token;
	}

	/**
	 *
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	private UserRegistrationFormPojo requestUserInfo(String accessToken) throws IOException, JSONException {
		UserRegistrationFormPojo userInfo = new UserRegistrationFormPojo();
		LOG.debug("Request Google for user info...");
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(socialUserInfoUrl + accessToken);
		int resCode = httpClient.executeMethod(getMethod);
		if (resCode == HttpStatus.SC_OK) {
			JSONObject jsonObject = new JSONObject(getMethod.getResponseBodyAsString());
			userInfo.setFirstName(jsonObject.optString("given_name"));
			userInfo.setLastName(jsonObject.optString("family_name"));
			userInfo.setEmail(jsonObject.optString("email"));
			if (jsonObject.optString("locale").equals("ru")) {
				userInfo.setLanguage(Language.RU);
			} else {
				userInfo.setLanguage(Language.EN);
			}
			userInfo.setNetworkType(EntryPointType.GOOGLE);
			userInfo.setNetworkUID(jsonObject.optString("id"));
			userInfo.setEmailVerified(jsonObject.optBoolean("verified_email"));
			// todo: move out date format to constants
			userInfo.setBirthday(jsonObject.optString("birthday"), "yyyy-MM-dd");
		} else {
			LOG.warn("Get Google userinfo request failed");
		}
		return userInfo;
	}

}
