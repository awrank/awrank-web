package com.awrank.web.backend.controller.payment;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.service.OrderService;
import com.awrank.web.model.service.impl.pojos.OrderCreateResultPojo;
import com.awrank.web.model.service.impl.pojos.PaymentWMFormPojo;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;

/**
 * @author Alex Polyakov
 */
@Controller
public class WebMoneyController extends AbstractController {

	@Autowired
	OrderService orderService;

	@RequestMapping(value = "/wm/order", method = RequestMethod.GET)
	public
	@ResponseBody
	String wmOrder(@RequestParam("payment_system_id") Long paymentSystemId, @RequestParam("product_id") Long productId) throws UnauthorizedException {
		OrderCreateResultPojo orderCreateResult = orderService.create(getUserDetails().getUserId(), paymentSystemId, productId);
		long orderId = orderCreateResult.getPaymentId();
		String webMoneyUrl = orderCreateResult.getPaymentUrl();
		String purse = orderCreateResult.getPaymentSystemExternalId();
		BigDecimal amount = orderCreateResult.getAmount();
		String paymentDesc = orderCreateResult.getProductNameLocalize();
		boolean testMode = orderCreateResult.isTestMode();

		try {
			paymentDesc = Base64.encode(paymentDesc.getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		StringBuilder res = new StringBuilder("<!DOCTYPE html>");
		res.append("<html>");
		res.append("<head>");
		res.append("<title>Payment</title>");
		res.append("<meta http-equiv=\"Content-Type\" content=\"text/html\" charset=\"utf-8\">");
		res.append("</head>");
		res.append("<body>");
		res.append("<form method=\"POST\" action=\"");
		res.append(webMoneyUrl);
		res.append("\">");
		res.append("<input type=\"hidden\" name=\"LMI_PAYMENT_AMOUNT\" value=\"");
		res.append(amount);
		res.append("\">");
		res.append("<input type=\"hidden\" name=\"LMI_PAYMENT_DESC_BASE64\" value=\"");
		res.append(paymentDesc);
		res.append("\">");
		res.append("<input type=\"hidden\" name=\"LMI_PAYMENT_NO\" value=\"");
		res.append(orderId);
		res.append("\">");
		res.append("<input type=\"hidden\" name=\"LMI_PAYEE_PURSE\" value=\"");
		res.append(purse);
		res.append("\">");
		if (testMode) {
			res.append("<input type=\"hidden\" name=\"LMI_SIM_MODE\" value=\"0\">");
		}
		res.append("</form>");
		res.append("<script type=\"text/javascript\">document.forms[0].submit();</script>");
		res.append("</body>");
		res.append("</html>");
		return res.toString();
	}

	@RequestMapping(value = "/wm/result", method = RequestMethod.POST)
	public void wmResult(HttpServletRequest request, HttpServletResponse response) {
		try {
			PaymentWMFormPojo conf = new PaymentWMFormPojo();
			conf.setLMI_PAYER_WM(request.getParameter("LMI_PAYER_WM"));
			conf.setLMI_LANG(request.getParameter("LMI_LANG"));
			conf.setLMI_HASH(request.getParameter("LMI_HASH"));
			conf.setLMI_MODE(request.getParameter("LMI_MODE"));
			conf.setLMI_PAYMENT_NO(request.getParameter("LMI_PAYMENT_NO"));
			conf.setLMI_PAYMENT_DESC(request.getParameter("LMI_PAYMENT_DESC"));
			conf.setLMI_PAYEE_PURSE(request.getParameter("LMI_PAYEE_PURSE"));
			conf.setLMI_PAYER_PURSE(request.getParameter("LMI_PAYER_PURSE"));
			conf.setLMI_SYS_TRANS_DATE(request.getParameter("LMI_SYS_TRANS_DATE"));
			conf.setLMI_SYS_INVS_NO(request.getParameter("LMI_SYS_INVS_NO"));
			conf.setLMI_SYS_TRANS_NO(request.getParameter("LMI_SYS_TRANS_NO"));
			conf.setLMI_DBLCHK(request.getParameter("LMI_DBLCHK"));
			conf.setLMI_PAYMENT_AMOUNT(request.getParameter("LMI_PAYMENT_AMOUNT"));
			getLogger().debug("action \"/wm/result\" " + conf);

			boolean paymentSuccess = orderService.paymentMW(conf);
			Writer writer = new BufferedWriter(response.getWriter());
			if (paymentSuccess) {
				try {
					writer.append("YES");
				} finally {
					writer.flush();
					writer.close();
				}
			} else {
				try {
					writer.append("NO");
				} finally {
					writer.flush();
					writer.close();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/wm/success", method = RequestMethod.POST)
	public void wmSuccess(HttpServletRequest request, HttpServletResponse response) {
		PaymentWMFormPojo conf = new PaymentWMFormPojo();
		conf.setLMI_SYS_TRANS_NO(request.getParameter("LMI_SYS_TRANS_NO"));
		conf.setLMI_SYS_TRANS_DATE(request.getParameter("LMI_SYS_TRANS_DATE"));
		conf.setLMI_LANG(request.getParameter("LMI_LANG"));
		conf.setLMI_SYS_INVS_NO(request.getParameter("LMI_SYS_INVS_NO"));
		conf.setLMI_PAYMENT_NO(request.getParameter("LMI_PAYMENT_NO"));
		getLogger().debug("action \"/wm/success\" " + conf);
	}

	@RequestMapping(value = "/wm/fail")
	public void wmFail(HttpServletRequest request, HttpServletResponse response) {
		PaymentWMFormPojo conf = new PaymentWMFormPojo();
		conf.setLMI_SYS_TRANS_NO(request.getParameter("LMI_SYS_TRANS_NO"));
		conf.setLMI_SYS_TRANS_DATE(request.getParameter("LMI_SYS_TRANS_DATE"));
		conf.setLMI_LANG(request.getParameter("LMI_LANG"));
		conf.setLMI_SYS_INVS_NO(request.getParameter("LMI_SYS_INVS_NO"));
		conf.setLMI_PAYMENT_NO(request.getParameter("LMI_PAYMENT_NO"));
		getLogger().debug("action \"/wm/fail\"" + conf);
	}
}
