package com.awrank.web.model.exception.social;

import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.AwRankException;

/**
 * Should be thrown in cases when request to social network to get some info e.g. {@code access_token} or user profile
 * info has failed.
 *
 * @author Andrew Stoyaltsev
 */
public class SocialNetworkRequestFailedException extends AwRankException {

	public SocialNetworkRequestFailedException(Message message) {
		super(message.name());
	}

}
