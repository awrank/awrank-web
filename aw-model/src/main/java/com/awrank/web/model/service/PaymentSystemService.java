package com.awrank.web.model.service;

import com.awrank.web.model.dao.pojos.PaymentFormPaymentSystemPojo;

import java.util.List;

/**
 * User: a_polyakov
 */
public interface PaymentSystemService extends AbstarctService {
	public List<PaymentFormPaymentSystemPojo> selectAvailable();
}
