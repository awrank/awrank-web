package com.awrank.web.model.domain.constant;

/**
 * @author Alex Polyakov
 */
public interface PaymentConst extends ExtendedAbstractAuditableConst {
	public static final String H_ORDER = "order";
	public static final String H_ORDER__USER__ID = H_ORDER + '.' + OrderConst.H_USER__ID;
	public static final String H_ORDER__PRODUCT_PROFILE__PRODUCT__NAME = H_ORDER + '.' + OrderConst.H_PRODUCT_PROFILE__PRODUCT__NAME;
	public static final String H_ORDER__ENDED_DATE = H_ORDER + '.' + OrderConst.H_ENDED_DATE;
	public static final String H_ORDER__STATUS = H_ORDER + '.' + OrderConst.H_STATUS;

	public static final String H_PAYMENT_SYSTEM = "paymentSystem";
	public static final String H_PAYMENT_SYSTEM__PAYMENT_SYSTEM_TYPE = H_PAYMENT_SYSTEM + '.' + PaymentSystemConst.H_PAYMENT_SYSTEM_TYPE;

	public static final String H_AMOUNT = "amount";

	public static final String H_CURRENCY = "currency";

	public static final String H_TRANSACTION_DATE = "transactionDate";
}
