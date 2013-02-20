package com.awrank.web.model.utils.price;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * User: a_polyakov
 */
public class PriceUtils {
	public static final int PRICE_CAPACITY = 2;
	public static final String SQL_PRICE_COLUMN_DEFINITION = "DECIMAL(10," + PRICE_CAPACITY + ")";
	public static final MathContext MATH_CONTEXT = MathContext.DECIMAL32;

	public static BigDecimal formatPrice(BigDecimal amount) {
		if (amount != null) {
			amount = amount.setScale(PRICE_CAPACITY, RoundingMode.HALF_EVEN);
		}
		return amount;
	}
}
