package com.awrank.web.model.dao.pojos;

import com.awrank.web.model.domain.PaymentSystem;
import com.awrank.web.model.domain.PaymentSystemType;
import com.awrank.web.model.domain.constant.PaymentSystemConst;
import com.awrank.web.model.utils.select.annotation.SelectField;
import com.awrank.web.model.utils.select.annotation.SelectFrom;

/**
 * User: a_polyakov
 */
public class PaymentFormPaymentSystemPojo {
	private final Long id;
	private final PaymentSystemType paymentSystemType;
	private final boolean testMode;

	@SelectFrom(PaymentSystem.class)
	public PaymentFormPaymentSystemPojo(
			@SelectField(PaymentSystemConst.H_ID) Long id,
			@SelectField(PaymentSystemConst.H_PAYMENT_SYSTEM_TYPE) PaymentSystemType paymentSystemType,
			@SelectField(PaymentSystemConst.H_TEST_MODE) boolean testMode) {
		this.id = id;
		this.paymentSystemType = paymentSystemType;
		this.testMode = testMode;
	}

	public Long getId() {
		return id;
	}

	public PaymentSystemType getPaymentSystemType() {
		return paymentSystemType;
	}

	public boolean isTestMode() {
		return testMode;
	}
}
