package com.awrank.web.model.service.impl.pojos;

import java.io.Serializable;

/**
 * POJO(well, actually a bean :) ) for user new password setting webform;
 *
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
public class UserNewPasswordFormPojo implements Serializable {

	 private String currentPassword;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String passwordConfirm;

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }


}
