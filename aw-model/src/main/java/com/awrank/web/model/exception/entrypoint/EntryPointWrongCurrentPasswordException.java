package com.awrank.web.model.exception.entrypoint;

import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.AwRankException;

/**
 * wrong current password
 * User: a_polyakov
 */
public class EntryPointWrongCurrentPasswordException extends AwRankException {
	private EntryPointWrongCurrentPasswordException() {
		super(Message.ENTRY_POINT_WRONG_CURRENT_PASSWORD.name());
	}

	private static EntryPointWrongCurrentPasswordException instance = new EntryPointWrongCurrentPasswordException();

	public static EntryPointWrongCurrentPasswordException getInstance() {
		return instance;
	}
}
