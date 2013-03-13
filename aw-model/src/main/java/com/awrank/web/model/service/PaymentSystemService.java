package com.awrank.web.model.service;

import com.awrank.web.model.dao.pojos.PaymentFormPaymentSystemPojo;
import com.awrank.web.model.domain.PaymentSystem;

import java.util.List;

/**
 * @author Alex Polyakov
 */
public interface PaymentSystemService extends AbstractService {
	public PaymentSystem findOne(Long id);

	public List<PaymentFormPaymentSystemPojo> selectAvailable();
}
