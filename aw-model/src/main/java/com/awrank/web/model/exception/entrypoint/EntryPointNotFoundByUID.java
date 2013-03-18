package com.awrank.web.model.exception.entrypoint;

import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.AwRankException;

/**
 * Exception for cases when {@code EntryPoint} was not found by {@code UID}.
 *
 * @author Andrew Stoyaltsev
 */
public class EntryPointNotFoundByUID extends AwRankException {

    public EntryPointNotFoundByUID() {
        super(Message.ENTRY_POINT_NOT_FOUND_BY_UID.name());
    }

    public EntryPointNotFoundByUID(EntryPointType type, String uid) {
        super(Message.ENTRY_POINT_NOT_FOUND_BY_UID.name());
        // todo: how to show input params in message?
    }
}
