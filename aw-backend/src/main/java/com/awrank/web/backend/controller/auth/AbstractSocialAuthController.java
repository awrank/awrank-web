package com.awrank.web.backend.controller.auth;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.social.SocialCallbackFailedException;
import com.awrank.web.model.exception.social.SocialNetworkRequestFailedException;
import com.awrank.web.model.service.SocialAuthService;
import com.awrank.web.model.service.impl.pojos.UserSocialRegistrationFormPojo;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

/**
 * Abstract social authentication controller.
 *
 * @author Andrew Stoyaltsev
 */
@Controller("abstractSocialAuthController")
public abstract class AbstractSocialAuthController extends AbstractController {

	private static Logger LOG = LoggerFactory.getLogger(AbstractSocialAuthController.class);

	public static final String AUTH_ACTION_LOGIN = "login";

	public static final String AUTH_ACTION_REGISTER = "register";

	@Autowired
	private SocialAuthService socialAuthService;

	/**
	 * Authentication action: login or registration
	 */
	private String action;

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
		Message message;
		if (StringUtils.hasLength(authCode)) {
			LOG.debug("Network response: auth code=" + authCode);

			String accessToken = requestAccessToken(authCode);
			LOG.debug("Network access_token: " + accessToken);

			if (StringUtils.hasLength(accessToken)) {
				UserSocialRegistrationFormPojo userInfo = requestUserInfo(accessToken);
				userInfo.setUserLocalAddress(request.getLocalAddr());
				userInfo.setUserRemoteAddress(request.getRemoteAddr());

				if (isActionLogin()) {
					return socialAuthService.login(userInfo, request);
				} else if (isActionRegister()) {
					return socialAuthService.register(userInfo, request);
				} else {
					message = Message.SOCIAL_NO_AUTH_ACTION_SPECIFIED;
				}
			} else {
				message = Message.SOCIAL_ACCESS_TOKEN_IS_NULL;
			}
		} else {
			message = Message.SOCIAL_AUTH_CODE_IS_NULL;
		}
		LOG.warn("Social callback handling failed: " + message.name());
		throw new SocialCallbackFailedException(message);
	}

	/**
	 * @param authCode
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	protected abstract String requestAccessToken(String authCode) throws IOException, JSONException, SocialNetworkRequestFailedException;

	/**
	 * @param accessToken
	 * @return
	 * @throws IOException
	 * @throws JSONException
	 */
	protected abstract UserSocialRegistrationFormPojo
	requestUserInfo(String accessToken) throws IOException, JSONException, SocialNetworkRequestFailedException;

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
