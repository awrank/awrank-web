package com.awrank.web.model.exception.social;

import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.AwRankException;

/**
 * Should be thrown in case when callback from social network has been processed with failure.
 *
 * @author Andrew Stoyaltsev
 */
public class SocialCallbackFailedException extends AwRankException {

	public SocialCallbackFailedException(Message message) {
		super(message.name());
	}
}
