package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.DatedAbstractAuditable;

import javax.persistence.*;

/**
 * PaymentSystem
 * User: a_polyakov
 */
@Entity
@Table(name = "payment_system")
public class PaymentSystem extends DatedAbstractAuditable {
	/**
	 * Type of payment system.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_system_type", nullable = false, updatable = false)
	private PaymentSystemType paymentSystemType;
	/**
	 * Url for payment
	 */
	@Column(name = "payment_system_url", nullable = false, updatable = false)
	private String systemUrl;
	/**
	 * your Id in payment system
	 * webmoney purse
	 * 2checkout sid
	 */
	@Column(name = "external_id", nullable = false, updatable = false)
	private String externalId;
	/**
	 * your username in payment system
	 */
	@Column(name = "username", nullable = false, updatable = false)
	private String username;
	/**
	 * your password in payment system
	 */
	@Column(name = "password", nullable = false, updatable = false)
	private String password;
	/**
	 * secret word for verification payment
	 */
	@Column(name = "secret_word", nullable = false)
	private String secretWord;
	/**
	 * enable/disable payment system
	 */
	@Column(name = "enable", nullable = false)
	private boolean enable;
	/**
	 * payment system should be used in test mode
	 */
	@Column(name = "test_mode", nullable = false)
	private boolean testMode;

	public PaymentSystem() {
	}

	public PaymentSystemType getPaymentSystemType() {
		return paymentSystemType;
	}

	public void setPaymentSystemType(PaymentSystemType paymentSystemType) {
		this.paymentSystemType = paymentSystemType;
	}

	public String getSystemUrl() {
		return systemUrl;
	}

	public void setSystemUrl(String systemUrl) {
		this.systemUrl = systemUrl;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecretWord() {
		return secretWord;
	}

	public void setSecretWord(String secretWord) {
		this.secretWord = secretWord;
	}

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public boolean isTestMode() {
		return testMode;
	}

	public void setTestMode(boolean testMode) {
		this.testMode = testMode;
	}
}
