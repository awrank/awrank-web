package com.awrank.web.model.dao;

import com.awrank.web.model.dao.pojos.PaymentCheckPojo;
import com.awrank.web.model.dao.pojos.PaymentHistoryFormPaymentPojo;

import java.util.List;

/**
 * @author Alex Polyakov
 */
public interface PaymentCustomDao {
	public List<PaymentHistoryFormPaymentPojo> getPaymentHistory(Long userId);
	public PaymentCheckPojo getPaymentCheckPojo(Long paymentId);
}
