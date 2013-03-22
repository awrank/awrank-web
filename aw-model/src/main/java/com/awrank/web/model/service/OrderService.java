package com.awrank.web.model.service;

import com.awrank.web.model.domain.Order;
import com.awrank.web.model.service.impl.pojos.OrderCreateResultPojo;
import com.awrank.web.model.service.impl.pojos.PaymentWMFormPojo;

public interface OrderService extends AbstractService {
	/**
	 * create order for product and payment system
	 *
	 * @param entryHistoryId
	 * @param paymentSystemId
	 * @param productProfileId
	 * @return
	 */
	public OrderCreateResultPojo create(Long entryHistoryId, Long paymentSystemId, Long productProfileId);

	/**
	 * complete payment to payment system webmoney
	 *
	 * @param paymentWMFormPojo
	 * @return
	 */
	public boolean paymentMW(PaymentWMFormPojo paymentWMFormPojo);

	/**
	 * complete payment to payment system 2CheckOut
	 *
	 * @param merchant_order_id
	 * @param sid
	 * @param total
	 * @param order_number
	 * @param key
	 * @return
	 */
	public boolean paymentTwoCheckOut(String merchant_order_id, String sid, String total, String order_number, String key);

	/**
	 * find paid order which today is the limit on the number of requests per day
	 *
	 * @param userId
	 * @return
	 */
	public Order findOneStatusPaidAndLimitDayAndNow(Long userId);
}
