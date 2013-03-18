package com.awrank.web.model.domain.constant;

/**
 * @author Alex Polyakov
 */
public interface OrderConst extends AbstractUserRelatedEntityAuditableConst {
	public static final String H_PRODUCT_PROFILE = "productProfile";
	public static final String H_PRODUCT_PROFILE__PRODUCT__NAME = H_PRODUCT_PROFILE + '.' + ProductProfileConst.H_PRODUCT__NAME;
}
