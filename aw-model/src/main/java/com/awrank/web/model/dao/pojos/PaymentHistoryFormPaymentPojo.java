package com.awrank.web.model.dao.pojos;

import com.awrank.web.model.domain.Payment;
import com.awrank.web.model.domain.PaymentSystemType;
import com.awrank.web.model.domain.constant.PaymentConst;
import com.awrank.web.model.enums.Currency;
import com.awrank.web.model.utils.json.MyXmlLocalDateTimeSerializer;
import com.awrank.web.model.utils.select.annotation.SelectField;
import com.awrank.web.model.utils.select.annotation.SelectFrom;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.joda.time.LocalDateTime;

import java.math.BigDecimal;

//import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author Alex Polyakov
 */
public class PaymentHistoryFormPaymentPojo {
	private final Long id;
	private final String productName;
	private final BigDecimal amount;
	private final Currency currency;
	private final PaymentSystemType paymentSystemType;
	//	@JsonProperty
	@JsonSerialize(using = MyXmlLocalDateTimeSerializer.class)
	private final LocalDateTime date_begin;
	@JsonSerialize(using = MyXmlLocalDateTimeSerializer.class)
	private final LocalDateTime date_end;


	@SelectFrom(Payment.class)
	public PaymentHistoryFormPaymentPojo(
			@SelectField(PaymentConst.H_ID) Long id,
			@SelectField(PaymentConst.H_ORDER__PRODUCT_PROFILE__PRODUCT__NAME) String productName,
			@SelectField(PaymentConst.H_AMOUNT) BigDecimal amount,
			@SelectField(PaymentConst.H_CURRENCY) Currency currency,
			@SelectField(PaymentConst.H_PAYMENT_SYSTEM__PAYMENT_SYSTEM_TYPE) PaymentSystemType paymentSystemType,
			@SelectField(PaymentConst.H_TRANSACTION_DATE) LocalDateTime date_begin,
			@SelectField(PaymentConst.H_ORDER__ENDED_DATE) LocalDateTime date_end
	) {
		this.id = id;
		this.productName = productName;
		this.amount = amount;
		this.currency = currency;
		this.paymentSystemType = paymentSystemType;
		this.date_begin = date_begin;
		this.date_end = date_end;
	}

	public Long getId() {
		return id;
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

	//	@JsonSerialize(using = MyXmlLocalDateTimeSerializer.class)
	public LocalDateTime getDate_begin() {
		return date_begin;
	}

	//	@JsonSerialize(using = JodaSerializers.LocalDateTimeSerializer.class)
//	@JsonSerialize(using = MyXmlLocalDateTimeSerializer.class)
//	@JsonFormat(pattern = .)
	public LocalDateTime getDate_end() {
		return date_end;
	}
}
