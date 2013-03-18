package com.awrank.web.backend.controller.rest.payment;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.backend.exception.ForbiddenException;
import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.dao.pojos.PaymentHistoryFormPaymentPojo;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.service.PaymentService;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Alex Polyakov
 */
@Controller
@RequestMapping(value = "/rest/payment_history")
public class PaymentHistoryController extends AbstractController {
	@Autowired
	private PaymentService paymentService;

	/**
	 * Returns payment history
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public
	@ResponseBody
	Iterable<PaymentHistoryFormPaymentPojo> list() throws UnauthorizedException, ForbiddenException {
		AWRankingUserDetails details = getUserDetails();
		checkHasAnyRole(Role.ROLE_ADMIN, Role.ROLE_USER);
		return paymentService.getPaymentHistory(details.getUserId());
	}
}
