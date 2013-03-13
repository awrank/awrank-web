package com.awrank.web.model.service;

import com.awrank.web.model.domain.Payment;

public interface PaymentService extends AbstractService {
	public Payment findOne(Long paymentId);

	public Payment save(Payment payment);
}
