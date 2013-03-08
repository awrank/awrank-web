package com.awrank.web.backend.exception;

import com.awrank.web.model.enums.Message;

/**
 * User not have role
 * User: a_polyakov
 */
public class ForbiddenException extends AwRankControllerException {
	private static ForbiddenException instance = new ForbiddenException();

	private ForbiddenException() {
		super(Message.ERROR_ACCESS.name());
	}

	public static ForbiddenException getInstance() {
		return instance;
	}
}
