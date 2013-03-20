package com.awrank.web.model.service.impl.pojos;

import com.awrank.web.model.domain.Language;
import com.awrank.web.model.enums.SecretQuestion;

import java.io.Serializable;
import java.util.Map;

/**
 * POJO bean for user registration form.
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

	private String remoteIP;
	
	public String getRemoteIP() {
		return remoteIP;
	}

	public void setRemoteIP(String remoteIP) {
		this.remoteIP = remoteIP;
	}

	private String localIP;

	public String getLocalIP() {
		return localIP;
	}

	public void setLocalIP(String localIP) {
		this.localIP = localIP;
	}
	
	private String userLocalAddress;

	private String userRemoteAddress;

	private SecretQuestion secretQuestionCode;

	private String secretQuestionAnswer;

	public String getSecretQuestionAnswer() {
		return secretQuestionAnswer;
	}

	public void setSecretQuestionAnswer(String secretQuestionAnswer) {
		this.secretQuestionAnswer = secretQuestionAnswer;
	}

	public SecretQuestion getSecretQuestionCode() {
		return secretQuestionCode;
	}

	public void setSecretQuestionCode(SecretQuestion secretQuestionCode) {
		this.secretQuestionCode = secretQuestionCode;
	}

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

	public void fillWith(Map<String, String> in) {

		this.setEmail(in.get("email"));
		this.setFirstName(in.get("firstName"));
		this.setLastName(in.get("lastName"));
		this.setPassword(in.get("password"));
		this.setLanguage(Language.valueOf(in.get("language")));
		this.setSecretQuestionCode(SecretQuestion.valueOf(in.get("questionDicCode")));
		this.setSecretQuestionAnswer(in.get("answer"));
		this.setLocalIP(in.get("localIP"));
		this.setRemoteIP(in.get("remoteIP"));
	}

}