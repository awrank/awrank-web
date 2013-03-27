package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.PaymentCustomDao;
import com.awrank.web.model.dao.PaymentDao;
import com.awrank.web.model.dao.pojos.PaymentCheckPojo;
import com.awrank.web.model.dao.pojos.PaymentHistoryFormPaymentPojo;
import com.awrank.web.model.domain.Payment;
import com.awrank.web.model.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentServiceImpl extends AbstractServiceImpl implements PaymentService {
	@Autowired
	public PaymentDao paymentDao;
	@Autowired
	public PaymentCustomDao paymentCustomDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Payment findOne(Long paymentId) {
		return paymentDao.findOne(paymentId);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PaymentHistoryFormPaymentPojo> getPaymentHistory(Long userId) {
		List<PaymentHistoryFormPaymentPojo> list = paymentCustomDao.getPaymentHistory(userId);
		return list;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Payment save(Payment payment) {
		return paymentDao.save(payment);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public PaymentCheckPojo getPaymentCheckPojo(Long paymentId){
		PaymentCheckPojo checkPojo=paymentCustomDao.getPaymentCheckPojo(paymentId);
		return checkPojo;
	}
}
