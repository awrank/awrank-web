package com.awrank.web.model.dao.pojos;

import com.awrank.web.model.domain.ProductProfile;
import com.awrank.web.model.domain.constant.ProductProfileConst;
import com.awrank.web.model.utils.select.annotation.SelectField;
import com.awrank.web.model.utils.select.annotation.SelectFrom;

import java.math.BigDecimal;

/**
 * User: a_polyakov
 */
public class PricingFormProductProfilePojo {
	private final Long id;
	private final String productName;
	private final String productDescription;
	private final int productPeriodInDays;
	private final int productCountDailyRequest;
	private final int productCountMonthlyRequest;
	private final BigDecimal price;
	private final BigDecimal discount;

	@SelectFrom(ProductProfile.class)
	public PricingFormProductProfilePojo(
			@SelectField(ProductProfileConst.H_ID) Long id,
			@SelectField(ProductProfileConst.H_PRODUCT__NAME) String productName,
			@SelectField(ProductProfileConst.H_PRODUCT__DESCRIPTION) String productDescription,
			@SelectField(ProductProfileConst.H_PRODUCT__PERIOD_IN_DAYS) int productPeriodInDays,
			@SelectField(ProductProfileConst.H_PRODUCT__COUNT_DAILY_REQUEST) int productCountDailyRequest,
			@SelectField(ProductProfileConst.H_PRODUCT__COUNT_MONTHLY_REQUEST) int productCountMonthlyRequest,
			@SelectField(ProductProfileConst.H_PRICE) BigDecimal price,
			@SelectField(ProductProfileConst.H_DISCOUNT) BigDecimal discount
	) {
		this.id = id;
		this.productName = productName;
		this.productDescription = productDescription;
		this.productPeriodInDays = productPeriodInDays;
		this.productCountDailyRequest = productCountDailyRequest;
		this.productCountMonthlyRequest = productCountMonthlyRequest;
		this.price = price;
		this.discount = discount;
	}

	public Long getId() {
		return id;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public int getProductPeriodInDays() {
		return productPeriodInDays;
	}

	public int getProductCountDailyRequest() {
		return productCountDailyRequest;
	}

	public int getProductCountMonthlyRequest() {
		return productCountMonthlyRequest;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getDiscount() {
		return discount;
	}
}
