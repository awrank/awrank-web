package com.awrank.web.model.service.impl.pojos;

import com.awrank.web.common.constants.AppConstants;
import com.awrank.web.model.domain.Language;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.enums.SecretQuestion;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * POJO bean for user profile data (firstName, lastName etc.).
 *
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
public class UserProfileDataFormPojo implements Serializable {

	private Long userId;

	private String apiKey;

	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	private String firstName;

	private String lastName;

	private String networkUID;

	private Language language;

	private String userLocalAddress;

	private String userRemoteAddress;

	private LocalDateTime birthday;

	private String birthdayAsFormattedString;

	private String secretAnswer;

	private SecretQuestion secretQuestion;

	public void fillWithUserData(User user) {
		this.setUserId(user.getId());
		this.setApiKey(user.getApiKey());
		this.setBirthday(user.getBirthday());
		this.setFirstName(user.getFirstName());
		this.setEmail(user.getEmail());
		this.setLanguage(user.getLanguage());
		this.setLastName(user.getLastName());
		this.setSecretAnswer(user.getSecretAnswer());
		this.setSecretQuestion(user.getSecretQuestionDicCode());
	}

	@SuppressWarnings("rawtypes")
	public Map toMap(){
		String tb = getBirthdayAsFormattedString();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("apiKey", apiKey);
		map.put("birthdayAsFormattedString", tb);
		map.put("firstName", firstName);
		map.put("lastName", lastName);
		map.put("email", email);
		map.put("networkUID", networkUID);
		map.put("secretAnswer", secretAnswer);
		map.put("secretQuestion", secretQuestion);
		//map.put("userId", userId);
		return map;
	}

	public void fillWith(Map<String, String> in) {
		
		this.setFirstName(in.get("firstName"));
		this.setLastName(in.get("lastName"));
		this.setLanguage(Language.valueOf(in.get("language")));
		this.setSecretQuestion(SecretQuestion.valueOf(in.get("questionDicCode")));
		this.setSecretAnswer(in.get("answer"));
		this.setBirthdayAsFormattedString(in.get("birthdayAsFormattedString"));
	}
	
	public String getBirthdayAsFormattedString() {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(AppConstants.DateFormat.DF_yyyyMMdd_minus);
		if (birthday != null) {
			return birthday.toString(formatter);
		} else {
			return "0000-00-00";
		}
	}

	public void setBirthdayAsFormattedString(String date) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(AppConstants.DateFormat.DF_yyyyMMdd_minus);
		this.birthday = formatter.parseLocalDateTime(date);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
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

	public String getNetworkUID() {
		return networkUID;
	}

	public void setNetworkUID(String networkUID) {
		this.networkUID = networkUID;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
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

	public LocalDateTime getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDateTime birthday) {
		this.birthday = birthday;
	}

	public String getSecretAnswer() {
		return secretAnswer;
	}

	public void setSecretAnswer(String secretAnswer) {
		this.secretAnswer = secretAnswer;
	}

	public SecretQuestion getSecretQuestion() {
		return secretQuestion;
	}

	public void setSecretQuestion(SecretQuestion secretQuestion) {
		this.secretQuestion = secretQuestion;
	}
}
