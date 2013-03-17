package com.awrank.web.backend.controller.auth;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.Language;
import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.AwRankException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotFoundByUID;
import com.awrank.web.model.exception.social.SocialCallbackFailedException;
import com.awrank.web.model.exception.social.SocialEmailNotProvidedException;
import com.awrank.web.model.exception.social.SocialNetworkRequestFailedException;
import com.awrank.web.model.service.DictionaryService;
import com.awrank.web.model.service.SocialAuthService;
import com.awrank.web.model.service.impl.pojos.UserSocialRegistrationFormPojo;
import org.json.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

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
public abstract class AbstractSocialAuthController extends AbstractController {

	private static Logger LOG = LoggerFactory.getLogger(AbstractSocialAuthController.class);

	public static final String AUTH_ACTION_LOGIN = "login";

	public static final String AUTH_ACTION_REGISTER = "register";

	@Autowired
	private SocialAuthService socialAuthService;

	@Autowired
	private DictionaryService dictionaryService;

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

	//protected Map handleNetworkCallback(HttpServletRequest request) throws Exception {
	protected ModelAndView handleNetworkCallback(HttpServletRequest request) throws Exception {
		String authCode = request.getParameter("code");
		Map<String, String> responseMap = null;
		try {
			Message message = null;
			if (StringUtils.hasLength(authCode)) {
				LOG.debug("Network response: auth code=" + authCode);

				String accessToken = requestAccessToken(authCode);
				LOG.debug("Network access_token: " + accessToken);

				if (StringUtils.hasLength(accessToken)) {
					UserSocialRegistrationFormPojo userInfo = requestUserInfo(accessToken);
					userInfo.setUserLocalAddress(request.getLocalAddr());
					userInfo.setUserRemoteAddress(request.getRemoteAddr());

					if (isActionLogin()) {
						responseMap = socialAuthService.login(userInfo, request);
						//ModelAndView mav = new ModelAndView("index");
						//mav.addObject("responseMap", responseMap);
						//return mav;
					} else if (isActionRegister()) {
						responseMap = socialAuthService.register(userInfo, request);
						//ModelAndView mav = new ModelAndView("index");
						//mav.addObject("responseMap", responseMap);
						//return mav;
					} else {
						message = Message.SOCIAL_NO_AUTH_ACTION_SPECIFIED;
					}
				} else {
					message = Message.SOCIAL_ACCESS_TOKEN_IS_NULL;
				}
			} else {
				message = Message.SOCIAL_AUTH_CODE_IS_NULL;
			}
			if (message != null) {
				LOG.warn("Social callback handling failed: " + message.name());
				throw new SocialCallbackFailedException(message);
			}
		} catch (AwRankException e) {
			responseMap = new HashMap<String, String>();
			responseMap.put("result", "failure");
			responseMap.put("errorCode", e.getMessage());
			String errorTitle = isActionLogin()
					? Message.SOCIAL_LOGIN_FAILED_TITLE.name()
					: Message.SOCIAL_REGISTER_FAILED_TITLE.name();
			responseMap.put("errorTitle", errorTitle);
		}

		ModelAndView mav = new ModelAndView("socialResponse");
		String successTitle = isActionLogin()
				? Message.YOU_LOGGED_IN_SUCCESSFULLY.name()
				: Message.YOU_REGISTERED_SUCCESSFULLY.name();
		responseMap.put("successTitle", successTitle);
		mav.addObject("responseMap", responseMap);
		return mav;
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

	public Boolean isActionLogin() {
		return this.action.equals(AUTH_ACTION_LOGIN);
	}

	public Boolean isActionRegister() {
		return this.action.equals(AUTH_ACTION_REGISTER);
	}

	/* getters & setters */

	public String getAction() {
		return action;
	}

}
