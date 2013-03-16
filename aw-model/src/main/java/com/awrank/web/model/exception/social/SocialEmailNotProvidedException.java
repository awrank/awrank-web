package com.awrank.web.model.exception.social;

import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.AwRankException;

/**
 * Thrown when social network did not provide us with user email during request of getting user profile info.
 *
 * @author Andrew Stoyaltsev
 */
public class SocialEmailNotProvidedException extends AwRankException {

    public SocialEmailNotProvidedException() {
        super(Message.SOCIAL_NETWORK_EMAIL_NOT_PROVIDED.name());
    }
}
