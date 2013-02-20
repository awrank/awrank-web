package com.awrank.web.model.service.impl.pojos;

import java.io.Serializable;

/**
 * POJO(well, actually a bean :) ) for user registration via "our" webform;
 *
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
public class UserRegistrationFormPOJO implements Serializable {

    private String firstName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    private String lastName;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    private String apiKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
//	private ELanguage language;
//	public ELanguage getLanguage() {
//		return language;
//	}
//	public void setLanguage(ELanguage language) {
//		this.language = language;
//	}

    /**
     * local and remote addresses needed for email verification: email shall be verified from the same IP
     * the registration was initiated;
     */
    private String userLocalAddr;

    public String getUserLocalAddr() {
        return userLocalAddr;
    }

    public void setUserLocalAddr(String userLocalAddr) {
        this.userLocalAddr = userLocalAddr;
    }

    public String getUserRemoteAddr() {
        return userRemoteAddr;
    }

    public void setUserRemoteAddr(String userRemoteAddr) {
        this.userRemoteAddr = userRemoteAddr;
    }

    private String userRemoteAddr;


}
