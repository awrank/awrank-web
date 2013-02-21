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
    ROLE_USER,

    /**
     * User which confirmed his email.
     */
    ROLE_USER_VERIFIED,

    /**
     * The role allows to edit user data.
     */
    ROLE_EDIT_USER,

    /**
     * The role allows to edit dictionary data.
     */
    ROLE_EDIT_DICTIONARY,
    
    /**
     *  Admin
     */
    ROLE_ADMIN
}
