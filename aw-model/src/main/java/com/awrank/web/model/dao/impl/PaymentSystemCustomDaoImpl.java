package com.awrank.web.model.dao.impl;

import com.awrank.web.model.dao.PaymentSystemCustomDao;
import com.awrank.web.model.dao.pojos.PaymentFormPaymentSystemPojo;
import com.awrank.web.model.domain.constant.PaymentSystemConst;
import com.awrank.web.model.utils.select.SelectUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alex Polyakov
 */
@Repository
public class PaymentSystemCustomDaoImpl extends AbstractDaoImpl implements PaymentSystemCustomDao {
	@Override
	public List<PaymentFormPaymentSystemPojo> selectAvailable() {
		List<PaymentFormPaymentSystemPojo> list = SelectUtils.getWrapperList(em, PaymentFormPaymentSystemPojo.class,
				"where o." + PaymentSystemConst.H_ENABLE + " = true " +
						"order by o." + PaymentSystemConst.H_ID, 0, 0);
		return list;
	}
}
