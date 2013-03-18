package com.awrank.web.model.utils.email;

import com.awrank.web.model.domain.StateChangeToken;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.enums.StateChangeTokenType;
import com.awrank.web.model.service.StateChangeTokenService;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * The class contains useful methods for email sending.
 *
 * @author Andrew Stoyaltsev
 */
public abstract class EmailHelper {

	private static Logger logger = Logger.getLogger(EmailHelper.class);

	/**
	 * Method provides building of verification link and sending email.
	 * @param emailActivationService service for email sending.
	 * @param user user
	 * @param email email
	 * @param password password (apiKey for social cases)
	 * @param localAddress local IP address
	 * @param remoteAddress remote IP address
	 * @return true - if email sent successfully, false - otherwise.
	 */
	public static boolean sendVerificationEmail(StateChangeTokenService emailActivationService, User user,
												String email, String password,
												String localAddress, String remoteAddress) {

		boolean result = false;
		try {
			String key = SMTPAuthenticator.getHashed256(email + "." + password + "." + localAddress + "." + remoteAddress);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("localAddr", localAddress);
			params.put("remoteAddr", remoteAddress);
			params.put("testactivation_email", email);
			params.put("testactivation_password", password);
			emailActivationService.send(params);

 			/* store to db information about verification email was sent */
			StateChangeToken stateChangeToken = new StateChangeToken();
			stateChangeToken.setToken(key);
			stateChangeToken.setType(StateChangeTokenType.USER_EMAIL_VERIFICATION);
			stateChangeToken.setUser(user);
			stateChangeToken.setValue(email);
			stateChangeToken.setIpAddress(remoteAddress);//Check later if we need remote or local IP here
			emailActivationService.save(stateChangeToken);
			result = true;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return result;
	}

}
