package com.awrank.web.model.domain.constant;

/**
 *
 */
public enum ERole {
    /**
     * user after registration
     * пользователь сразу после регистрации
     */
    USER,
    /**
     * user to confirm your email
     * пользователь подтвердивший свой email
     */
    USER_VERIFIED,
    EDIT_USER,
    EDIT_DICTIONARY;
}
