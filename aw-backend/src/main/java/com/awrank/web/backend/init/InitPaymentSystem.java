package com.awrank.web.backend.init;

import com.awrank.web.model.dao.PaymentSystemDao;
import com.awrank.web.model.domain.PaymentSystem;
import com.awrank.web.model.domain.PaymentSystemType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Initialization of payment systems for the application
 *
 * @author Alex Polyakov
 */
@Service
public class InitPaymentSystem {

	@Autowired
	private PaymentSystemDao paymentSystemDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void init() {

		if (paymentSystemDao.findAllByExternalId("Z226057239329").isEmpty()) {
			PaymentSystem paymentSystem = new PaymentSystem();
			paymentSystem.setPaymentSystemType(PaymentSystemType.WEB_MONEY);
			paymentSystem.setSystemUrl("https://merchant.webmoney.ru/lmi/payment.asp");
			paymentSystem.setExternalId("Z226057239329");
			paymentSystem.setUsername("228055417603");
			paymentSystem.setPassword("");
			paymentSystem.setSecretWord("ldkfjbglkjsdbgkjsabdkfjnasdkfnjlaskdfn");
			paymentSystem.setEnable(true);
			paymentSystem.setTestMode(true);
			paymentSystemDao.save(paymentSystem);
		}

		if (paymentSystemDao.findAllByExternalId("1817037").isEmpty()) {
			PaymentSystem paymentSystem = new PaymentSystem();
			paymentSystem.setPaymentSystemType(PaymentSystemType.TWO_CHECKOUT);
			paymentSystem.setSystemUrl("https://www.2checkout.com/checkout/spurchase");
			paymentSystem.setExternalId("1817037");
			paymentSystem.setUsername("APIuser1817037");
			paymentSystem.setPassword("APIpass1817037");
			paymentSystem.setSecretWord("tango");
			paymentSystem.setEnable(false);
			paymentSystem.setTestMode(true);
			paymentSystemDao.save(paymentSystem);
		}

		if (paymentSystemDao.findAllByExternalId("1978002").isEmpty()) {
			PaymentSystem paymentSystem = new PaymentSystem();
			paymentSystem.setPaymentSystemType(PaymentSystemType.TWO_CHECKOUT);
			paymentSystem.setSystemUrl("https://www.2checkout.com/checkout/spurchase");
			paymentSystem.setExternalId("1978002");
			paymentSystem.setUsername("APIuser1978002");
			paymentSystem.setPassword("DrSqzXOA7");
			paymentSystem.setSecretWord("QwZBs9V6yYgPUA");
			paymentSystem.setEnable(true);
			paymentSystem.setTestMode(true);
			paymentSystemDao.save(paymentSystem);
		}
	}
}
