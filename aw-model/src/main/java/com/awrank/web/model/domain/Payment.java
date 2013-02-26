package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.ExtendedAbstractAuditable;
import com.awrank.web.model.utils.price.PriceUtils;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The <b>Payment</b> class is the primary for the entire payments.
 *
 * @author Alex Polyakov
 * @author Eugene Solomka
 */
@Entity
@Table(name = "payments")
public class Payment extends ExtendedAbstractAuditable {

	/**
	 * Order to which the Payment is bound.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "order_id", nullable = false, updatable = false)
	private Order order;

	/**
	 * Type of payment.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "payment_type", nullable = false, updatable = false)
	private PaymentType paymentType;

	/**
	 * Payable amount.
	 */
	@Column(name = "amount", columnDefinition = PriceUtils.SQL_PRICE_COLUMN_DEFINITION, nullable = false)
	private BigDecimal amount;

	/**
	 * Payable amount in local currency.
	 */
	@Column(name = "amount_currency", columnDefinition = PriceUtils.SQL_PRICE_COLUMN_DEFINITION, nullable = false)
	private BigDecimal amountCurrency;

	/**
	 * Status of the payment.
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
	private PaymentStatus status = PaymentStatus.UNALLOCATED;

	/**
	 * Transaction reference of the payment.
	 */
	@Column(name = "transaction_ref", nullable = true, length = 128)
	private String transactionRef;

	/**
	 * Reference of the payment.
	 */
	@Column(name = "payment_ref", nullable = true, length = 128)
	private String paymentRef;

	/**
	 * Payer reference of the payment.
	 */
	@Column(name = "payer_ref", nullable = true, length = 128)
	private String payerRef;

	/**
	 * Description of the payment.
	 */
	@Column(name = "description", nullable = true, length = 255)
	private String description;

	public Payment() {
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmountCurrency() {
		return amountCurrency;
	}

	public void setAmountCurrency(BigDecimal amountCurrency) {
		this.amountCurrency = amountCurrency;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public String getTransactionRef() {
		return transactionRef;
	}

	public void setTransactionRef(String transactionRef) {
		this.transactionRef = transactionRef;
	}

	public String getPaymentRef() {
		return paymentRef;
	}

	public void setPaymentRef(String paymentRef) {
		this.paymentRef = paymentRef;
	}

	public String getPayerRef() {
		return payerRef;
	}

	public void setPayerRef(String payerRef) {
		this.payerRef = payerRef;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
