package com.awrank.web.backend.controller.auth;

import com.awrank.web.common.constants.AppConstants;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.Language;
import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.social.SocialNetworkRequestFailedException;
import com.awrank.web.model.service.impl.pojos.UserSocialRegistrationFormPojo;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;

/**
 * This class provides authentication process (request/handling callbacks) for user via
 * <a href="google.com">Google</a> OAuth.
 *
 * @author Andrew Stoyaltsev
 */
@Controller
public class GoogleAuthController extends AbstractSocialAuthController {

	private static Logger LOG = LoggerFactory.getLogger(GoogleAuthController.class);

	//@Value("${oauth.google.auth.url}")
	private String socialAuthUrl = "https://accounts.google.com/o/oauth2/auth";

	//@Value("${oauth.google.token.url}")
	private String socialTokenUrl = "https://accounts.google.com/o/oauth2/token";

	//@Value("${oauth.google.clientId}")
	private String clientId = "833205848531.apps.googleusercontent.com";

	//@Value("${oauth.google.clientSecret}")
	private String clientSecret = "S56giFrR-y7kDNn2qPFV1hRY";

	//@Value("${oauth.google.redirect.uri}")
	private String redirectUri = "http://awrank.com:8080/awrank/googleOAuthCallback";

	//@Value("${oauth.google.userinfo.url}")
	private String socialUserInfoUrl = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";

	@RequestMapping(value = "/user/{action}/google", method = RequestMethod.GET)
	public String authViaGoogle(@PathVariable(value = "action") String action) throws IOException {
		return super.authViaNetwork(action);
	}

	protected String getRequestAuthCodeURI() throws IOException {
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

	@RequestMapping(value = "/googleOAuthCallback", produces = "application/json")
	@ResponseBody()
	public Map googleCallback(HttpServletRequest request) throws Exception {
		String state = request.getParameter("state");
		Message message;
		if (StringUtils.hasLength(state)) {
			if (state.equals("code")) {
				String error = request.getParameter("error");
				if (StringUtils.hasLength(error)) {
					LOG.debug("Negative response from Google: " + error);
					throw new SocialNetworkRequestFailedException(Message.SOCIAL_NEGATIVE_RESPONSE_RECEIVED);
				}
				return super.handleNetworkCallback(request);
			} else {
				message = Message.SOCIAL_INVALID_GOOGLE_STATE_PARAM_VALUE;
			}
		} else {
			message = Message.SOCIAL_MISSING_GOOGLE_STATE_PARAM;
		}
		LOG.warn("Google callback failed: " + message.name());
		throw new SocialNetworkRequestFailedException(message);
	}

	protected String requestAccessToken(String authCode)
			throws IOException, JSONException, SocialNetworkRequestFailedException {

		String token;
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
			throw new SocialNetworkRequestFailedException(Message.SOCIAL_REQUEST_ACCESS_TOKEN_FAILED);
		}
		return token;
	}

	protected UserSocialRegistrationFormPojo requestUserInfo(String accessToken)
			throws IOException, JSONException, SocialNetworkRequestFailedException {

		UserSocialRegistrationFormPojo userInfo = new UserSocialRegistrationFormPojo();
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
			String birthday = jsonObject.optString("birthday");
			if (StringUtils.hasLength(birthday)) {
				userInfo.setBirthday(birthday, AppConstants.DateFormat.DF_yyyyMMdd_minus);
			}
		} else {
			LOG.warn("Get Google userinfo request failed");
			throw new SocialNetworkRequestFailedException(Message.SOCIAL_REQUEST_USER_PROFILE_FAILED);
		}
		return userInfo;
	}

}
