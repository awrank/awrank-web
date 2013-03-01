package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.AbstractUserRelatedEntityAuditable;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.enums.StateChangeTokenType;

import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import javax.persistence.*;

/**
 * The idea is to have tokens, required on some User's etc. state change: email verification, password change etc.
 *
 * @author Alex Polyakov
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "state_change_token")
public class StateChangeToken extends AbstractUserRelatedEntityAuditable {

	/**
	 * Generated code which is included into verification link (e.g. some hash value) etc. - state change token
	 */
	@Column(name = "token", nullable = false)
	private String token;

	/**
	 * IP address from which a user came into the system via verification URL.
	 */
	@Column(name = "ip_address", nullable = false)
	private String ipAddress;

	/**
	 * New value of some state changed using this token.
	 */
	@Column(name = "new_value", nullable = true)
	private String newValue;

	/**
	 * Current value of some state (user email in case of email verification, current passwoed in case of password change etc.).
	 */
	@Column(name = "value", nullable = false)
	private String value;

	/**
	 * A date when state was changes with thsi token (verifies email, changed password etc.).
	 */
	@Column(name = "token_used_at", nullable = true)
	@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime tokenUsedAtDate;

	/**
	 * Token type, e.g. USER_EMAIL_VERIFICATIOM, USER_PASSWORD_CHANGE etc.
	 */
	@Column(name = "type", nullable = false)
	@Enumerated(EnumType.STRING)
	private StateChangeTokenType type;
	
	public StateChangeTokenType getType() {
		return type;
	}

	public void setType(StateChangeTokenType type) {
		this.type = type;
	}
	/**
	 * Constructor
	 */
	public StateChangeToken() {
	}

    /* getters & setters */

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getNewValue() {
		return newValue;
	}

	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public LocalDateTime getTokenUsedAtDate() {
		return tokenUsedAtDate;
	}

	public void setTokenUsedAtDate(LocalDateTime tokenUsedAtDate) {
		this.tokenUsedAtDate = tokenUsedAtDate;
	}
}
