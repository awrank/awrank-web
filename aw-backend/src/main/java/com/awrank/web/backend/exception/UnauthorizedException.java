package com.awrank.web.backend.exception;

/**
 * Authentication error or a timeout session
 *
 * @author Alex Polyakov
 */
public class UnauthorizedException extends AwRankControllerException {

	private static UnauthorizedException instance = new UnauthorizedException();

	private UnauthorizedException() {
		super("Unauthorized");
	}

	public static UnauthorizedException getInstance() {
		return instance;
	}
}
