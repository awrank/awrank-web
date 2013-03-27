package com.awrank.web.model.service;

import com.awrank.web.model.domain.Order;
import com.awrank.web.model.service.impl.pojos.OrderCreateResultPojo;
import org.joda.time.LocalDateTime;

public interface OrderService extends AbstractService {

	public Order findOne(Long orderId);

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
	 * find paid order which today is the limit on the number of requests per day
	 *
	 * @param userId
	 * @return
	 */
	public Order findOneStatusPaidAndLimitDayAndNow(Long userId);

	/**
	 * complete payment, order, create limit, diary.
	 *
	 * @param paymentId
	 * @param transactionRef
	 * @param transactionDate
	 * @param paymentRef
	 * @param payerRef
	 */
	public void payment(Long paymentId, String transactionRef, LocalDateTime transactionDate, String paymentRef, String payerRef);

	/**
	 * write error payment
	 *
	 * @param paymentId
	 */
	public void errorPayment(Long paymentId);
}
