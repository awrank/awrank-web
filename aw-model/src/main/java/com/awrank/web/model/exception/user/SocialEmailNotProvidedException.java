package com.awrank.web.model.exception.user;

import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.AwRankException;

/**
 * TODO: Description
 *
 * @author Andrew Stoyaltsev
 */
public class SocialEmailNotProvidedException extends AwRankException {

    public SocialEmailNotProvidedException() {
        super(Message.SOCIAL_NETWORK_EMAIL_NOT_PROVIDED.name());
    }
}
