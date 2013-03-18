package com.awrank.web.model.domain.constant;

/**
 * @author Alex Polyakov
 */
public interface AbstractUserRelatedEntityAuditableConst extends DatedAbstractAuditableConst {
	public static final String H_USER = "user";
	public static final String H_USER__ID = H_USER + '.' + UserConst.H_ID;
}
