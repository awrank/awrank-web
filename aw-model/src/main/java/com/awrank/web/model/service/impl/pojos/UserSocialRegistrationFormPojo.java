package com.awrank.web.model.service.impl.pojos;

import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.Language;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.utils.select.annotation.SelectField;
import com.awrank.web.model.utils.select.annotation.SelectFrom;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

/**
 * POJO bean for social registration.
 *
 * @author Andrew Stoyaltsev
 */
public class UserSocialRegistrationFormPojo implements Serializable {

    private Long id;

    private String apiKey;

    private String firstName;

    private String lastName;

    private String email;

    private Language language;

    private String networkUID;

    private EntryPointType networkType;

    private boolean emailVerified;

    private LocalDateTime birthday;

    private String ip;

    private String userLocalAddress;

    private String userRemoteAddress;

	public UserSocialRegistrationFormPojo() {
	}

	@SelectFrom(User.class)
	public UserSocialRegistrationFormPojo(
			@SelectField("id") Long id,
			@SelectField("email") String email,
			@SelectField("firstName") String firstName,
			@SelectField("lastName") String lastName) {
		this.id = id;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public User createUser() {
        User user = new User();
        user.setApiKey(getApiKey());
        user.setFirstName(getFirstName());
        user.setLastName(getLastName());
        user.setEmail(getEmail());
        user.setLanguage(getLanguage());
        user.setAuthorizationFailsCount(0);
        user.setBirthday(getBirthday());
        return user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNetworkUID() {
        return networkUID;
    }

    public void setNetworkUID(String networkUID) {
        this.networkUID = networkUID;
    }

    public EntryPointType getNetworkType() {
        return networkType;
    }

    public void setNetworkType(EntryPointType networkType) {
        this.networkType = networkType;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public void setBirthday(String date, String format) {
        DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
        setBirthday(formatter.parseLocalDateTime(date));
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserLocalAddress() {
        return userLocalAddress;
    }

    public void setUserLocalAddress(String userLocalAddress) {
        this.userLocalAddress = userLocalAddress;
    }

    public String getUserRemoteAddress() {
        return userRemoteAddress;
    }

    public void setUserRemoteAddress(String userRemoteAddress) {
        this.userRemoteAddress = userRemoteAddress;
    }
}
