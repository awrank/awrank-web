package com.awrank.web.backend.controller.payment;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.dao.pojos.PaymentCheckPojo;
import com.awrank.web.model.service.OrderService;
import com.awrank.web.model.service.PaymentService;
import com.awrank.web.model.service.impl.pojos.OrderCreateResultPojo;
import org.apache.commons.codec.digest.DigestUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author Alex Polyakov
 */
@Controller
public class TwoCheckOutController extends AbstractController {

	@Autowired
	private OrderService orderService;

	@Autowired
	private PaymentService paymentService;

	/**
	 * create order
	 * https://www.2checkout.com/blog/knowledge-base/merchants/tech-support/3rd-party-carts/parameter-sets/pass-through-product-parameter-set/
	 *
	 * @param paymentSystemId
	 * @param productId
	 * @return
	 * @throws UnauthorizedException
	 */
	@RequestMapping(value = "/twocheckout/order", method = RequestMethod.GET)
	public String twocheckoutOrder(ModelMap model, @RequestParam("payment_system_id") Long paymentSystemId, @RequestParam("product_id") Long productId) throws UnauthorizedException {

		OrderCreateResultPojo orderCreateResult = orderService.create(getUserDetails().getEntryHistoryId(), paymentSystemId, productId);
		long paymentId = orderCreateResult.getPaymentId();
		String sid = orderCreateResult.getPaymentSystemExternalId();
		BigDecimal amount = orderCreateResult.getAmount();
		String productNameLocalize = orderCreateResult.getProductNameLocalize();
		boolean testMode = orderCreateResult.isTestMode();

		model.put("sid", sid);
		model.put("merchant_order_id", paymentId);
		model.put("testMode", testMode);
		model.put("li_0_name", productNameLocalize);
		model.put("li_0_price", amount);
		return "twocheckout/order";
	}

	/**
	 * payment complete
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/twocheckout/result", method = RequestMethod.GET)
	public void twocheckoutResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String merchant_order_id = request.getParameter("merchant_order_id");
		String sid = request.getParameter("sid");
		String total = request.getParameter("total");
		String order_number = request.getParameter("order_number");
		String key = request.getParameter("key");
		getLogger().debug("action \"/twocheckout/result\" {merchant_order_id:" + merchant_order_id + ", sid:" + sid + ", total:" + total + ", order_number:" + order_number + ", key:" + key + "}");

		Long paymentId = Long.parseLong(merchant_order_id);
		PaymentCheckPojo checkPojo = paymentService.getPaymentCheckPojo(paymentId);
		String hashString = checkPojo.getPaymentSystemSecretWord() + checkPojo.getPaymentSystemExternalId() + order_number + checkPojo.getPrice();
		String hash = DigestUtils.md5Hex(hashString).toUpperCase();
		boolean paymentSuccess = key.equals(hash);
		if (paymentSuccess) {
			getLogger().debug("2CO check passed. Payment [" + paymentId + "]. Internal String:" + hashString + "; Internal MD5:" + hash + " External MD5:" + key);
			// https://github.com/2Checkout/2checkout-java/wiki/Sale_Retrieve
			// TODO sale
			// Sale sale = TwocheckoutSale.retrieve(order_number, paymentSystem.getUsername(), paymentSystem.getPassword());
			// sale.getDatePlaced();
			// sale.getCustomer().getCustomerId();
			// transaction id
			orderService.payment(paymentId, order_number, new LocalDateTime(), order_number, "unknown");
			response.sendRedirect("../index.html#payment_history");
		} else {
			getLogger().error("2CO check not passed. Payment [" + paymentId + "]. Internal String:" + hashString + "; Internal MD5:" + hash + " External MD5:" + key);
			orderService.errorPayment(paymentId);
			response.sendRedirect("../index.html#order");
		}
	}
}
