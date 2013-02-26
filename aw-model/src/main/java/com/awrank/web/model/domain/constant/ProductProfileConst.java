package com.awrank.web.model.domain.constant;

/**
 * User: a_polyakov
 */
public interface ProductProfileConst extends DatedAbstractAuditableConst {
	public static final String H_PRODUCT = "product";
	public static final String H_PRODUCT__NAME = H_PRODUCT + '.' + ProductConst.H_NAME;
	public static final String H_PRODUCT__DESCRIPTION = H_PRODUCT + '.' + ProductConst.H_DESCRIPTION;
	public static final String H_PRODUCT__PERIOD_IN_DAYS = H_PRODUCT + '.' + ProductConst.H_PERIOD_IN_DAYS;
	public static final String H_PRODUCT__COUNT_DAILY_REQUEST = H_PRODUCT + '.' + ProductConst.H_COUNT_DAILY_REQUEST;
	public static final String H_PRODUCT__COUNT_MONTHLY_REQUEST = H_PRODUCT + '.' + ProductConst.H_COUNT_MONTHLY_REQUEST;
	public static final String H_PRODUCT__PRODUCT_VISIBILITY = H_PRODUCT + '.' + ProductConst.H_PRODUCT_VISIBILITY;
	public static final String H_PRODUCT__IS_ACTIVE = H_PRODUCT + '.' + ProductConst.H_IS_ACTIVE;

	public static final String H_PRICE = "price";

	public static final String H_DISCOUNT = "discount";

	public static final String H_STARTED_DATE = "startedDate";


}
