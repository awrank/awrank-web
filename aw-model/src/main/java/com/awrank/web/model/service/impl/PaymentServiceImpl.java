package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.PaymentDao;
import com.awrank.web.model.domain.Payment;
import com.awrank.web.model.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentServiceImpl extends AbstractServiceImpl implements PaymentService {
	@Autowired
	public PaymentDao paymentDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Payment findOne(Long paymentId) {
		return paymentDao.findOne(paymentId);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Payment save(Payment payment) {
		return paymentDao.save(payment);
	}
}
