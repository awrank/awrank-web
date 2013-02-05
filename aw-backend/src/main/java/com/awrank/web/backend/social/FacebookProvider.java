package com.awrank.web.backend.social;

import com.awrank.web.model.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.facebook.api.impl.FacebookTemplate;
import org.springframework.stereotype.Repository;

/**
 * Facebook provider bean
 * It is required to use <facebook:init/> tag to create form for login into facebook
 *
 * @author akakunin
 */
@Repository("facebookProvider")
public class FacebookProvider {

	@Value("${social.facebook.appId}")
	private String apiKey;

	@Value("${social.facebook.appSecret}")
	private String appSecret;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	/**
	 * Create Facebook template for currently logged-in user
	 *
	 * @return
	 */
	public FacebookTemplate createTemplate(User user) {
//		if (user.getFacebookAccessToken() != null) {
////			return new FacebookTemplate(user.getFacebookAccessToken());
//			return new FacebookTemplate();
//		} else {
//			return null;
//		}
		return null;
	}
}