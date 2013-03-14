package com.awrank.web.model.service.impl.pojos;

import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.Language;
import com.awrank.web.model.domain.User;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.Serializable;
import java.util.UUID;

/**
 * POJO(well, actually a bean :) ) for user profile data (user name etc.);
 *
 * @author Olga Korokhina
 */
@SuppressWarnings("serial")
public class UserProfileDataFormPojo implements Serializable {

	public static String DATE_FORMAT_STRING = "yyyy-MM-dd";
	private Long userId;

	private String apiKey;

	private String firstName;

	private String lastName;
	
	private Language language;

	private String userLocalAddress;

	private String userRemoteAddress;

	private String networkUID;

	private EntryPointType networkType;

	private LocalDateTime birthday;
	private String birthdayAsFormattedString;

	public void fillWithUserData(User user){
		
		this.setUserId(user.getId());
		this.setApiKey(user.getApiKey());
		this.setBirthday(user.getBirthday());
		this.setFirstName(user.getFirstName());
		this.setLanguage(user.getLanguage());
		this.setLastName(user.getLastName());
		
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long id) {
		this.userId = id;
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

	public void setBirthday(LocalDateTime birthday) {
		this.birthday = birthday;
	}
	
	public LocalDateTime getBirthday() {
		return birthday;
	}

	public String getBirthdayAsFormattedString(){
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern(UserProfileDataFormPojo.DATE_FORMAT_STRING);
		if(birthday != null) return birthday.toString(formatter);
		else return "0000-00-00";
	}
	
	public void setBirthdayAsFormattedString(String date){
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern(UserProfileDataFormPojo.DATE_FORMAT_STRING);
		LocalDateTime data = formatter.parseLocalDateTime(date); 
		this.birthday = data;
	}

	
/*
	public void setBirthday(String date, String format) {
		
		if(format == null) format = UserProfileDataFormPojo.DATE_FORMAT_STRING;
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		setBirthday(formatter.parseLocalDateTime(date));
	}
*/
	public EntryPointType getNetworkType() {
		return networkType;
	}

	public void setNetworkType(EntryPointType networkType) {
		this.networkType = networkType;
	}
}
