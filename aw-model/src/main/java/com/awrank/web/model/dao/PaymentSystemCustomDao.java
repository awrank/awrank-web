package com.awrank.web.model.dao;

import com.awrank.web.model.dao.pojos.PaymentFormPaymentSystemPojo;

import java.util.List;

/**
 * @author Alex Polyakov
 */
public interface PaymentSystemCustomDao {
	public List<PaymentFormPaymentSystemPojo> selectAvailable();
}
