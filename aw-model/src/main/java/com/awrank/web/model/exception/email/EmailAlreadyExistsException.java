package com.awrank.web.model.exception.email;

import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.AwRankException;

/**
 * Should be thrown in cases when identical email was found in DB.
 *
 * @author Andrew Stoyaltsev
 */
public class EmailAlreadyExistsException extends AwRankException {

    public EmailAlreadyExistsException() {
        super(Message.EMAIL_ALREADY_EXISTS.name());
    }
}
