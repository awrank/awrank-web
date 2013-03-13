package com.awrank.web.model.domain.constant;

/**
 * @author Alex Polyakov
 */
public interface ProductConst extends DatedAbstractAuditableConst {
	public static final String H_NAME = "name";

	public static final String H_DESCRIPTION = "description";

	public static final String H_PERIOD_IN_DAYS = "periodInDays";

	public static final String H_COUNT_DAILY_REQUEST = "countDailyRequest";

	public static final String H_COUNT_MONTHLY_REQUEST = "countMonthlyRequest";

	public static final String H_PRODUCT_VISIBILITY = "productVisibility";

	public static final String H_IS_ACTIVE = "isActive";
}
