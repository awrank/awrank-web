package com.awrank.web.model.service;

import com.awrank.web.model.domain.Order;
import com.awrank.web.model.service.impl.pojos.OrderCreateResultPojo;
import com.awrank.web.model.service.impl.pojos.PaymentWMFormPojo;

import java.util.Map;

public interface OrderService extends AbstractService {
	public OrderCreateResultPojo create(Long entryHistoryId, Long paymentSystemId, Long productProfileId);

	public boolean paymentMW(PaymentWMFormPojo paymentWMFormPojo);

	public boolean paymentTwoCheckOut(String merchant_order_id, String sid, String total, String order_number, String key);

	/**
	 * can be used to track any of the events in payment system
	 * setting of
	 * https://www.2checkout.com/va/notifications/ins_settings
	 *
	 * @param params
	 */
	public void eventTwoCheckOut(Map<String, String> params);

	public Order findOneStatusPaidAndLimitDayAndNow(Long userId);
}
