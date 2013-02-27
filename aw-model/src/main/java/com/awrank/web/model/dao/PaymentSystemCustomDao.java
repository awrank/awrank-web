package com.awrank.web.model.dao;

import com.awrank.web.model.dao.pojos.PaymentFormPaymentSystemPojo;

import java.util.List;

/**
 * User: a_polyakov
 */
public interface PaymentSystemCustomDao {
	public List<PaymentFormPaymentSystemPojo> selectAvailable();
}
