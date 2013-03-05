package com.awrank.web.model.service.impl.pojos;

import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.Language;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;

/**
 * POJO(well, actually a bean :) ) for user registration via "our" webform;
 *
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 */
@SuppressWarnings("serial")
public class UserRegistrationFormPojo implements Serializable {

	private String firstName;
	
	private Long id;

	private String lastName;

	private String email;

	private String password;

	private Language language;

	private String apiKey;

	private String ip;

	private String userLocalAddress;

	private String userRemoteAddress;

	private String networkUID;

	private EntryPointType networkType;

	private boolean emailVerified;

	private LocalDateTime birthday;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	/**
	 * local and remote addresses needed for email verification: email shall be verified from the same IP
	 * the registration was initiated;
	 */

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

	public String getNetworkUID() {
		return networkUID;
	}

	public void setNetworkUID(String networkUID) {
		this.networkUID = networkUID;
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

	public EntryPointType getNetworkType() {
		return networkType;
	}

	public void setNetworkType(EntryPointType networkType) {
		this.networkType = networkType;
	}
}
