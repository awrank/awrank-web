package com.awrank.web.model.service.impl.pojos;

import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.Language;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.enums.SecretQuestion;

import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

/**
 * POJO(well, actually a bean :) ) for user registration via "our" webform;
 *
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 */
@SuppressWarnings("serial")
public class UserRegistrationFormPojo implements Serializable {

	private Long id;

	private String apiKey;

	private String firstName;

	private String lastName;

	private String email;

	private String password;

	private Language language;

	private String ip;

	private String userLocalAddress;

	private String userRemoteAddress;

    // todo: probably the fields below should be deleted if not used.

	private String secretQuestionAnswer;

	public String getSecretQuestionAnswer() {
		return secretQuestionAnswer;
	}

	public void setSecretQuestionAnswer(String secretQuestionAnswer) {
		this.secretQuestionAnswer = secretQuestionAnswer;
	}

	private EntryPointType networkType;

	private boolean emailVerified;
	
	private SecretQuestion secretQuestionCode;
	
	private String networkUID;

	public SecretQuestion getSecretQuestionCode() {
		return secretQuestionCode;
	}

	public void setSecretQuestionCode(SecretQuestion secretQuestionCode) {
		this.secretQuestionCode = secretQuestionCode;
	}

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

	/**
	 * stubs, for make this POJO work with divRegister.html. If password and confirmation are equal shall be checked on frontend page
	 */
	public String getPasswordConfirm() {
		return password;
	}

	public void setPasswordConfirm(String password) {
	
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

	public EntryPointType getNetworkType() {
		return networkType;
	}

	public void setNetworkType(EntryPointType networkType) {
		this.networkType = networkType;
	}
	
	public void fillWith(Map<String, String> in){
		
		this.setEmail(in.get("email"));
		this.setFirstName(in.get("firstName"));
		this.setLastName(in.get("lastName"));
		this.setPassword(in.get("password"));
		this.setLanguage(Language.valueOf(in.get("language")));
		this.setSecretQuestionCode(SecretQuestion.valueOf(in.get("questionDicCode")));
		this.setSecretQuestionAnswer(in.get("answer"));
	}
}
