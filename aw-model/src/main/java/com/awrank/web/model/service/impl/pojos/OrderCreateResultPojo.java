package com.awrank.web.model.service.impl.pojos;

import java.math.BigDecimal;

/**
 * TODO: description
 *
 * @author Alex Polyakov
 */
public class OrderCreateResultPojo {

	private final String paymentUrl;

	private final BigDecimal amount;

	private final String productNameLocalize;

	private final Long paymentId;

	private final String paymentSystemExternalId;

	private final boolean testMode;

	public OrderCreateResultPojo(String paymentUrl, BigDecimal amount, String productNameLocalize, Long paymentId,
								 String paymentSystemExternalId, boolean testMode) {

		this.paymentUrl = paymentUrl;
		this.amount = amount;
		this.productNameLocalize = productNameLocalize;
		this.paymentId = paymentId;
		this.paymentSystemExternalId = paymentSystemExternalId;
		this.testMode = testMode;
	}

	public String getPaymentUrl() {
		return paymentUrl;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public String getProductNameLocalize() {
		return productNameLocalize;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public String getPaymentSystemExternalId() {
		return paymentSystemExternalId;
	}

	public boolean isTestMode() {
		return testMode;
	}
}
