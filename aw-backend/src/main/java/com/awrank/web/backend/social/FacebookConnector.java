package com.awrank.web.backend.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.oauth2.GrantType;
import org.springframework.social.oauth2.OAuth2Operations;
import org.springframework.social.oauth2.OAuth2Parameters;

/**
 * @author Andrew Stoyaltsev
 */
public class FacebookConnector {

	@Autowired
	private Environment env;

	public void connect() {
//
//		FacebookConnectionFactory connectionFactory =
//				new FacebookConnectionFactory(
//						env.getProperty("social.facebook.appId"),
//						env.getProperty("social.facebook.appSecret"));
//		OAuth2Operations oauthOperations = connectionFactory.getOAuthOperations();
//		OAuth2Parameters params = new OAuth2Parameters();
//		params.setRedirectUri("https://localhost:8070/aw/callback/fb");
//		String authorizeUrl = oauthOperations.buildAuthorizeUrl(GrantType.AUTHORIZATION_CODE, params);
//		response.sendRedirect(authorizeUrl);



	}

}
