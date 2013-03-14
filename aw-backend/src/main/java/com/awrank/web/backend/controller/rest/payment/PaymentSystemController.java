package com.awrank.web.backend.controller.rest.payment;

import com.awrank.web.model.dao.pojos.PaymentFormPaymentSystemPojo;
import com.awrank.web.model.service.PaymentSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Alex Polyakov
 */
@Controller
@RequestMapping(value = "/rest/payment_system")
public class PaymentSystemController {
	@Autowired
	private PaymentSystemService paymentSystemService;

	/**
	 * Returns list of payment system pojo
	 */
	@PreAuthorize("permitAll")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public
	@ResponseBody
	Iterable<PaymentFormPaymentSystemPojo> list() {
		return paymentSystemService.selectAvailable();
	}

}
