package com.awrank.web.model.exception.user;

import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.AwRankException;

/**
 * TODO: Description
 *
 * @author Andrew Stoyaltsev
 */
public class EmailAlreadyExistsException extends AwRankException {

    public EmailAlreadyExistsException() {
        super(Message.EMAIL_ALREADY_EXISTS.name());
    }
}
