package com.awrank.web.model.service;

import com.awrank.web.model.dao.pojos.PaymentHistoryFormPaymentPojo;
import com.awrank.web.model.domain.Payment;

import java.util.List;

public interface PaymentService extends AbstractService {
	public Payment findOne(Long paymentId);

	public List<PaymentHistoryFormPaymentPojo> getPaymentHistory(Long userId);

	public Payment save(Payment payment);
}
