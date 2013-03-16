package com.awrank.web.backend.controller.auth;

import com.awrank.web.common.constants.AppConstants;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.Language;
import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.social.SocialNetworkRequestFailedException;
import com.awrank.web.model.service.impl.pojos.UserSocialRegistrationFormPojo;
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
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * This class provides authentication process (request/handling callbacks) for user via
 * <a href="facebook.com">Facebook</a> OAuth.
 *
 * @author Andrew Stoyaltsev
 */
@Controller
public class FacebookAuthController extends AbstractSocialAuthController {

	private static Logger LOG = LoggerFactory.getLogger(FacebookAuthController.class);

	//@Value("${oauth.facebook.auth.url}")
	private String socialAuthUrl = "https://www.facebook.com/dialog/oauth";

	//@Value("${oauth.facebook.token.url}")
	private String socialTokenUrl = "https://graph.facebook.com/oauth/access_token?";

	//@Value("${oauth.facebook.appId}")
	private String clientId = "347948558644172";

	//@Value("${oauth.facebook.appSecret}")
	private String clientSecret = "270be44d06652edad696a58438dfbd5a";

	//@Value("${oauth.facebook.redirect.uri}")
	private String redirectUri = "http://awrank.com:8080/awrank/facebookOAuthCallback";

	//@Value("${oauth.facebook.userinfo.url}")
	private String socialUserInfoUrl = "https://graph.facebook.com/me?access_token=";

	@RequestMapping(value = "/user/{action}/facebook", method = RequestMethod.GET)
	public String authViaFacebook(@PathVariable(value = "action") String action) throws IOException {
		return super.authViaNetwork(action);
	}

	protected String getRequestAuthCodeURI() throws IOException {
		StringBuilder queryString = new StringBuilder();
		queryString.append("client_id=").append(clientId).append("&");
		queryString.append("redirect_uri=").append(redirectUri).append("&");
		queryString.append("response_type=").append("code");
		String uri = socialAuthUrl + "?" + queryString;
		LOG.debug("Facebook request auth code URI: " + uri);
		return uri;
	}

	@RequestMapping(value = "/facebookOAuthCallback", produces = "application/json")
	@ResponseBody()
	public Map facebookCallback(HttpServletRequest request) throws Exception {
		return super.handleNetworkCallback(request);
	}

	protected String requestAccessToken(String authCode) throws IOException, SocialNetworkRequestFailedException {
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
			throw new SocialNetworkRequestFailedException(Message.SOCIAL_REQUEST_ACCESS_TOKEN_FAILED);
		}
		return token;
	}

	protected UserSocialRegistrationFormPojo requestUserInfo(String accessToken)
			throws IOException, JSONException, SocialNetworkRequestFailedException {

		UserSocialRegistrationFormPojo userInfo = new UserSocialRegistrationFormPojo();
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
				DateTimeFormatter formatter = DateTimeFormat.forPattern(AppConstants.DateFormat.DF_ddMMyyyy_slash);
				LocalDateTime birthday = LocalDateTime.parse(jsonObject.optString("birthday"), formatter);
				userInfo.setBirthday(birthday);
			}
		} else {
			LOG.warn("Get Facebook userinfo request failed");
			throw new SocialNetworkRequestFailedException(Message.SOCIAL_REQUEST_USER_PROFILE_FAILED);
		}
		return userInfo;
	}
}
