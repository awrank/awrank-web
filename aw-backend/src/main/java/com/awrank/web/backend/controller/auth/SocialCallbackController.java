package com.awrank.web.backend.controller.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Andrew Stoyaltsev
 */
@Controller
@RequestMapping(value = "/callback")
public class SocialCallbackController {

	/*
	@RequestMapping(value = "/fb", method = RequestMethod.POST)
	public String connectToFacebook(@FacebookAccessToken String accessToken,
								  @FacebookUserId String facebookUserId) {

		if (facebookUserId != null && accessToken != null) {
			// store facebook information
			String userName = getCurrentUser().getName();
			userService.updateFacebookAuthentication(userName, accessToken, facebookUserId);
		}
		return "redirect:/status";

	}
*/
}
