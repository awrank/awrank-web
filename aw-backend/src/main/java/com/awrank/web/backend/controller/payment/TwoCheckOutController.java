package com.awrank.web.backend.controller.payment;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.service.OrderService;
import com.awrank.web.model.service.impl.pojos.OrderCreateResultPojo;
import com.twocheckout.TwocheckoutCharge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alex Polyakov
 */
@Controller
public class TwoCheckOutController extends AbstractController {

	@Autowired
	OrderService orderService;

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
	public
	@ResponseBody
	String twocheckoutOrder(@RequestParam("payment_system_id") Long paymentSystemId, @RequestParam("product_id") Long productId) throws UnauthorizedException {

		OrderCreateResultPojo orderCreateResult = orderService.create(getUserDetails().getEntryHistoryId(), paymentSystemId, productId);
		long paymentId = orderCreateResult.getPaymentId();
		String sid = orderCreateResult.getPaymentSystemExternalId();
		BigDecimal amount = orderCreateResult.getAmount();
		String productNameLocalize = orderCreateResult.getProductNameLocalize();
		boolean testMode = orderCreateResult.isTestMode();
		System.out.println(sid);

		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sid", sid);
		params.put("mode", "2CO");
		params.put("merchant_order_id", String.valueOf(paymentId));
//		params.put("x_Receipt_Link_URL", "http://localhost:8080/aw-backend/twocheckout/result");
		if (testMode) {
			params.put("demo", "Y");
		}
		params.put("li_0_name", productNameLocalize);
		params.put("li_0_price", amount.toString());

		StringBuilder res = new StringBuilder("<!DOCTYPE html>");
		res.append("<html>");
		res.append("<head>");
		res.append("<title>Payment</title>");
		res.append("<meta http-equiv=\"Content-Type\" content=\"text/html\" charset=\"utf-8\">");
		res.append("</head>");
		res.append("<body>");
		res.append(TwocheckoutCharge.submit(params));
		res.append("</body>");
		res.append("</html>");
		return res.toString();
	}

	/**
	 * payment complite
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
		boolean paymentSuccess = orderService.paymentTwoCheckOut(merchant_order_id, sid, total, order_number, key);
		if (paymentSuccess) {
			response.sendRedirect("../index.html#payment_history");
		} else {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html");
			Writer writer = response.getWriter();
			writer.append("<!DOCTYPE html>");
			writer.append("<html>");
			writer.append("<head>");
			writer.append("<title>Payment</title>");
			writer.append("<meta http-equiv=\"Content-Type\" content=\"text/html\" charset=\"utf-8\">");
			writer.append("</head>");
			writer.append("<body>");
			writer.append("<script type=\"text/javascript\">history.go(-4);</script>");
			writer.append("</body>");
			writer.append("</html>");
			writer.close();
		}
	}

	/**
	 * now not use
	 * can be used to track any of the events in payment system
	 * setting of
	 * https://www.2checkout.com/va/notifications/ins_settings
	 * events:
	 * Order Created
	 * Fraud Status Changed
	 * Shipping Status Changed
	 * Invoice Status Changed
	 * Refund Issued
	 * Recurring Installment Success
	 * Recurring Installment Failed
	 * Recurring Stopped
	 * Recurring Complete
	 * Recurring Restarted
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/twocheckout/notification", method = RequestMethod.POST)
	public void twocheckoutEvent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HashMap<String, String> params = new HashMap<String, String>();
		for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
			if (entry.getValue().length == 1) {
				params.put(entry.getKey(), entry.getValue()[0]);
			} else {
				getLogger().error("Error data notification key:" + entry.getKey() + " value:" + entry.getValue());
			}
		}
		getLogger().debug("notification params:" + params);
		orderService.eventTwoCheckOut(params);
	}

}
