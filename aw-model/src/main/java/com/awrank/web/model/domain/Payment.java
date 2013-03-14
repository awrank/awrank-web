package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.ExtendedAbstractAuditable;
import com.awrank.web.model.enums.Currency;
import com.awrank.web.model.utils.price.PriceUtils;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

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
	 * Select payment system which created payment.
	 */
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "payment_system_id", nullable = false, updatable = false)
	private PaymentSystem paymentSystem;

	/**
	 * Payable amount in product currency.
	 */
	@Column(name = "amount", columnDefinition = PriceUtils.SQL_PRICE_COLUMN_DEFINITION, nullable = false)
	private BigDecimal amount;

	/**
	 * Product currency
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "currency", nullable = false)
	private Currency currency;

	/**
	 * Payable amount in original payment currency.
	 */
	@Column(name = "amount_currency", columnDefinition = PriceUtils.SQL_PRICE_COLUMN_DEFINITION, nullable = false)
	private BigDecimal amountCurrency;


	/**
	 * Original payment currency
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "original_currency", nullable = false)
	private Currency originalCurrency;

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
	 * Transaction date of the payment.
	 */
	@Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	@Column(name = "transaction_date", nullable = true)
	private LocalDateTime transactionDate;

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

	public PaymentSystem getPaymentSystem() {
		return paymentSystem;
	}

	public void setPaymentSystem(PaymentSystem paymentSystem) {
		this.paymentSystem = paymentSystem;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
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

	public Currency getOriginalCurrency() {
		return originalCurrency;
	}

	public void setOriginalCurrency(Currency originalCurrency) {
		this.originalCurrency = originalCurrency;
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

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
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
