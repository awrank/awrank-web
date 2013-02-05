package com.awrank.web.backend.exception;

/**
 * User: a_polyakov
 * <p/>
 * Authentication error or a timeout session
 * Ошибка аутентификации или таймаут сессии
 */
public class UnauthorizedException extends AwRankControllerException {

    private UnauthorizedException() {
        super("Unauthorized");
    }

    private static UnauthorizedException instance = new UnauthorizedException();

    public static UnauthorizedException getInstance() {
        return instance;
    }
}
