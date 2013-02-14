package com.awrank.web.model.enums;

/**
 * The enumeration describes roles.
 *
 * @author Alex Polyakov
 */
public enum Role {
    /**
     * Registered user.
     */
    USER,

    /**
     * User which confirmed his email.
     */
    USER_VERIFIED,

    /**
     * The role allows to edit user data.
     */
    EDIT_USER,

    /**
     * The role allows to edit dictionary data.
     */
    EDIT_DICTIONARY
}
