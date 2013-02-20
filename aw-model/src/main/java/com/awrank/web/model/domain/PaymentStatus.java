package com.awrank.web.model.domain;

/**
 * The <code>PaymentStatus</code> represents enum for payment status information.
 */
public enum PaymentStatus {
	/**
	 * Describes whether the payment is unallocated.
	 */
	UNALLOCATED,

	/**
	 * Describes whether the payment is allocated.
	 */
	ALLOCATED,

	/**
	 * Describes whether the payment is rejected.
	 */
	REJECTED
}
