package com.awrank.web.model.dao.pojos;

import com.awrank.web.model.domain.OrderStatus;
import com.awrank.web.model.domain.Payment;
import com.awrank.web.model.domain.constant.PaymentConst;
import com.awrank.web.model.utils.select.annotation.SelectField;
import com.awrank.web.model.utils.select.annotation.SelectFrom;

import java.math.BigDecimal;

/**
 * @author a_polyakov
 * Date: 27.03.13
 * Time: 15:06
 */
public class PaymentCheckPojo {
	private final OrderStatus orderStatus;
	private final String paymentSystemExternalId;
	private final BigDecimal price;
	private final boolean paymentIsTestMode;
	private final String paymentSystemSecretWord;

	@SelectFrom(Payment.class)
	public PaymentCheckPojo(
			@SelectField(PaymentConst.H_ORDER__STATUS)OrderStatus orderStatus,
			@SelectField(PaymentConst.H_PAYMENT_SYSTEM__EXTERNAL_ID)String paymentSystemExternalId,
			@SelectField(PaymentConst.H_AMOUNT)BigDecimal price,
			@SelectField(PaymentConst.H_PAYMENT_SYSTEM__TEST_MODE)boolean paymentIsTestMode,
			@SelectField(PaymentConst.H_PAYMENT_SYSTEM__SECRET_WORD)String paymentSystemSecretWord
	) {
		this.orderStatus = orderStatus;
		this.paymentSystemExternalId = paymentSystemExternalId;
		this.price = price;
		this.paymentIsTestMode = paymentIsTestMode;
		this.paymentSystemSecretWord = paymentSystemSecretWord;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public String getPaymentSystemExternalId() {
		return paymentSystemExternalId;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public boolean isPaymentIsTestMode() {
		return paymentIsTestMode;
	}

	public String getPaymentSystemSecretWord() {
		return paymentSystemSecretWord;
	}
}
