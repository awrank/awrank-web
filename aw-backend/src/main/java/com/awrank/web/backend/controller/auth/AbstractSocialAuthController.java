package com.awrank.web.backend.controller.auth;

import com.awrank.web.model.service.SocialAuthService;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Abstract social authentication controller.
 *
 * @author Andrew Stoyaltsev
 */
@Controller("abstractSocialAuthController")
public abstract class AbstractSocialAuthController {

	private static Logger LOG = LoggerFactory.getLogger(AbstractSocialAuthController.class);

	public static final String AUTH_ACTION_LOGIN = "login";

	public static final String AUTH_ACTION_REGISTER = "register";

	@Autowired
	private SocialAuthService socialAuthService;

	private String action;

	protected Map getNegativeResponseMap(String reason) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "failure");
		result.put("reason", reason);
		return result;
	}

	public String authViaNetwork(String action) throws IOException {
		this.action = action;
		String uri = getRequestAuthCodeURI();
		LOG.debug("Redirect to request auth code URI: " + uri);
		return "redirect:" + uri;
	}

	/**
	 * Method should implement creation of request authorization code URI respectively to chosen social network.
	 *
	 * @return response as {@code Map} instance.
	 */
	protected abstract String getRequestAuthCodeURI() throws IOException;

	protected Map handleNetworkCallback(HttpServletRequest request) throws Exception {
		String authCode = request.getParameter("code");
		String message;
		if (StringUtils.hasLength(authCode)) {
			LOG.debug("Network response: auth code=" + authCode);

			String accessToken = requestAccessToken(authCode);
			LOG.debug("Network access_token: " + accessToken);


			if (StringUtils.hasLength(accessToken)) {
				UserRegistrationFormPojo userInfo = requestUserInfo(accessToken);
				if (isActionLogin()) {
					return socialAuthService.login(userInfo, request);
				} else if (isActionRegister()) {
					return socialAuthService.register(userInfo, request);
				} else {
					message = "No auth action specified!";
				}
			} else {
				message = "Access token is null!";
			}
		} else {
			message = "Auth code is null";
		}
		LOG.warn(message);
		return getNegativeResponseMap(message);
	}

	/**
	 *
	 * @param authCode
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	protected abstract String requestAccessToken(String authCode) throws IOException, JSONException;

	/**
	 *
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	protected abstract UserRegistrationFormPojo requestUserInfo(String accessToken) throws IOException, JSONException;

	public boolean isActionLogin() {
		return this.action.equals(AUTH_ACTION_LOGIN);
	}

	public boolean isActionRegister() {
		return this.action.equals(AUTH_ACTION_REGISTER);
	}

	/* getters & setters */

	public String getAction() {
		return action;
	}

}
