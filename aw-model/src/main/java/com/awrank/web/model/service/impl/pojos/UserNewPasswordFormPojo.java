package com.awrank.web.model.service.impl.pojos;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * POJO bean for user new password setting form.
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
	
	@SuppressWarnings("rawtypes")
	public Map toMap(){
	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("password", password);
		map.put("currentPassword", currentPassword);
		map.put("passwordConfirm", passwordConfirm);
		map.put("remoteIP", remoteIP);
		map.put("localIP", localIP);
	
		return map;
	}

	public void fillWith(Map<String, String> in) {
		
		this.setCurrentPassword(in.get("currentPassword"));
		this.setPassword(in.get("password"));
		this.setPasswordConfirm(in.get("passwordConfirm"));
		this.setLocalIP(in.get("localIP"));
		this.setRemoteIP(in.get("remoteIP"));
		
	}
}