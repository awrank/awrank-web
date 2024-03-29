package com.awrank.web.backend.controller.rest.payment;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.backend.exception.ForbiddenException;
import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.dao.pojos.PaymentFormPaymentSystemPojo;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.service.PaymentSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Alex Polyakov
 */
@Controller
@RequestMapping(value = "/rest/payment_system")
public class PaymentSystemController extends AbstractController {
	@Autowired
	private PaymentSystemService paymentSystemService;

	/**
	 * Returns list of payment system pojo
	 */
	//@PreAuthorize("permitAll")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public
	@ResponseBody
	Iterable<PaymentFormPaymentSystemPojo> list() throws UnauthorizedException, ForbiddenException {
		checkHasAnyRole(Role.ROLE_ADMIN, Role.ROLE_USER);
		return paymentSystemService.selectAvailable();
	}

}
