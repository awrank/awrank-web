package com.awrank.web.model.service;

import com.awrank.web.model.service.impl.pojos.OrderCreateResultPojo;
import com.awrank.web.model.service.impl.pojos.PaymentWMFormPojo;

public interface OrderService extends AbstractService {
	public OrderCreateResultPojo create(Long userId, Long paymentSystemId, Long productProfileId);

	public boolean paymentMW(PaymentWMFormPojo paymentWMFormPojo);
}
