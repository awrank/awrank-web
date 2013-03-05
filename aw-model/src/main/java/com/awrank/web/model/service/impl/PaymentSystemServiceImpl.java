package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.PaymentSystemCustomDao;
import com.awrank.web.model.dao.PaymentSystemDao;
import com.awrank.web.model.dao.pojos.PaymentFormPaymentSystemPojo;
import com.awrank.web.model.service.PaymentSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: a_polyakov
 */
@Service
public class PaymentSystemServiceImpl extends AbstarctServiceImpl implements PaymentSystemService {
	@Autowired
	public PaymentSystemDao paymentSystemDao;
	@Autowired
	public PaymentSystemCustomDao paymentSystemCustomDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PaymentFormPaymentSystemPojo> selectAvailable() {
		return paymentSystemCustomDao.selectAvailable();
	}

}
