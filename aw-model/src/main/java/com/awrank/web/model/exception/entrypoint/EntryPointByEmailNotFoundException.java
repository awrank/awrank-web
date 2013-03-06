package com.awrank.web.model.exception.entrypoint;

import com.awrank.web.model.enums.Message;
import com.awrank.web.model.exception.AwRankException;

/**
 * Entry point by email not found
 * User: a_polyakov
 */
public class EntryPointByEmailNotFoundException extends AwRankException {

	private EntryPointByEmailNotFoundException() {
		super(Message.ENTRY_POINT_BY_EMAIL_NOT_FOUND.name());
	}

	private static EntryPointByEmailNotFoundException instance = new EntryPointByEmailNotFoundException();

	public static EntryPointByEmailNotFoundException getInstance() {
		return instance;
	}
}
