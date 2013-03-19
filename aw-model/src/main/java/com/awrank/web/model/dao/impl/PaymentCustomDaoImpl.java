package com.awrank.web.model.dao.impl;

import com.awrank.web.model.dao.PaymentCustomDao;
import com.awrank.web.model.dao.pojos.PaymentHistoryFormPaymentPojo;
import com.awrank.web.model.domain.constant.PaymentConst;
import com.awrank.web.model.utils.select.SelectUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alex Polyakov
 */
@Repository
public class PaymentCustomDaoImpl extends AbstractDaoImpl implements PaymentCustomDao {
	@Override
	public List<PaymentHistoryFormPaymentPojo> getPaymentHistory(Long userId) {
		List<PaymentHistoryFormPaymentPojo> list = SelectUtils.getWrapperList(em, PaymentHistoryFormPaymentPojo.class,
				"where o." + PaymentConst.H_ORDER__USER__ID + '=' + userId, 0, 0);
		return list;
	}
}
