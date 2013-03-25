package com.awrank.web.model.dao.pojos;

import com.awrank.web.model.domain.OrderStatus;
import com.awrank.web.model.domain.Payment;
import com.awrank.web.model.domain.PaymentSystemType;
import com.awrank.web.model.domain.constant.PaymentConst;
import com.awrank.web.model.enums.Currency;
import com.awrank.web.model.utils.json.MyXmlDateSerializer;
import com.awrank.web.model.utils.json.MyXmlLocalDateTimeSerializer;
import com.awrank.web.model.utils.select.annotation.SelectField;
import com.awrank.web.model.utils.select.annotation.SelectFrom;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Alex Polyakov
 */
public class PaymentHistoryFormPaymentPojo {
	private final Long id;
	@JsonSerialize(using = MyXmlDateSerializer.class)
	private final Date date_created;
	private final String productName;
	private final BigDecimal amount;
	private final Currency currency;
	private final PaymentSystemType paymentSystemType;
	@JsonSerialize(using = MyXmlLocalDateTimeSerializer.class)
	private final LocalDateTime date_begin;
	@JsonSerialize(using = MyXmlLocalDateTimeSerializer.class)
	private final LocalDateTime date_end;
	private final OrderStatus status;


	@SelectFrom(Payment.class)
	public PaymentHistoryFormPaymentPojo(
			@SelectField(PaymentConst.H_ID) Long id,
			@SelectField(PaymentConst.H_CREATED_DATE) Date date_created,
			@SelectField(PaymentConst.H_ORDER__PRODUCT_PROFILE__PRODUCT__NAME) String productName,
			@SelectField(PaymentConst.H_AMOUNT) BigDecimal amount,
			@SelectField(PaymentConst.H_CURRENCY) Currency currency,
			@SelectField(PaymentConst.H_PAYMENT_SYSTEM__PAYMENT_SYSTEM_TYPE) PaymentSystemType paymentSystemType,
			@SelectField(PaymentConst.H_TRANSACTION_DATE) LocalDateTime date_begin,
			@SelectField(PaymentConst.H_ORDER__ENDED_DATE) LocalDateTime date_end,
			@SelectField(PaymentConst.H_ORDER__STATUS) OrderStatus status
	) {
		this.id = id;
		this.date_created = date_created;
		this.productName = productName;
		this.amount = amount;
		this.currency = currency;
		this.paymentSystemType = paymentSystemType;
		this.date_begin = date_begin;
		this.date_end = date_end;
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public Date getDate_created() {
		return date_created;
	}

	public String getProductName() {
		return productName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public PaymentSystemType getPaymentSystemType() {
		return paymentSystemType;
	}

	public LocalDateTime getDate_begin() {
		return date_begin;
	}

	public LocalDateTime getDate_end() {
		return date_end;
	}

	public OrderStatus getStatus() {
		return status;
	}
}
