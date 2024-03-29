package com.awrank.web.backend.controller.payment;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.dao.pojos.PaymentCheckPojo;
import com.awrank.web.model.domain.OrderStatus;
import com.awrank.web.model.service.OrderService;
import com.awrank.web.model.service.PaymentService;
import com.awrank.web.model.service.PaymentSystemService;
import com.awrank.web.model.service.ProductService;
import com.awrank.web.model.service.impl.pojos.OrderCreateResultPojo;
import com.awrank.web.model.service.impl.pojos.PaymentWMFormPojo;
import com.awrank.web.model.utils.user.PasswordUtils;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

/**
 * @author Alex Polyakov
 */
@Controller
public class WebMoneyController extends AbstractController {

	@Autowired
	OrderService orderService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private PaymentSystemService paymentSystemService;

	@Autowired
	private ProductService productService;


	/**
	 * create order
	 *
	 * @param model
	 * @param paymentSystemId
	 * @param productId
	 * @return
	 * @throws UnauthorizedException
	 */
	@RequestMapping(value = "/wm/order", method = RequestMethod.GET)
	public String wmOrder(ModelMap model, @RequestParam("payment_system_id") Long paymentSystemId, @RequestParam("product_id") Long productId) throws UnauthorizedException {
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

		model.put("webMoneyUrl", webMoneyUrl);
		model.put("LMI_PAYMENT_AMOUNT", amount);
		model.put("LMI_PAYMENT_DESC_BASE64", paymentDesc);
		model.put("LMI_PAYMENT_NO", orderId);
		model.put("LMI_PAYEE_PURSE", purse);
		model.put("testMode", testMode);
		return "wm/order";
	}

	/**
	 * payment complete
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/wm/result", method = RequestMethod.POST)
	public void wmResult(HttpServletRequest request, HttpServletResponse response) {
		try {
			PaymentWMFormPojo conf = new PaymentWMFormPojo();
			conf.setLMI_DBLCHK(request.getParameter("LMI_DBLCHK"));
			conf.setLMI_HASH(request.getParameter("LMI_HASH"));
			conf.setLMI_LANG(request.getParameter("LMI_LANG"));
			conf.setLMI_MODE(request.getParameter("LMI_MODE"));
			conf.setLMI_PAYMENT_NO(request.getParameter("LMI_PAYMENT_NO"));
			conf.setLMI_PAYMENT_AMOUNT(request.getParameter("LMI_PAYMENT_AMOUNT"));
			conf.setLMI_PAYMENT_DESC(request.getParameter("LMI_PAYMENT_DESC"));
			conf.setLMI_PAYEE_PURSE(request.getParameter("LMI_PAYEE_PURSE"));
			conf.setLMI_PAYER_PURSE(request.getParameter("LMI_PAYER_PURSE"));
			conf.setLMI_PAYER_WM(request.getParameter("LMI_PAYER_WM"));
			conf.setLMI_SYS_TRANS_DATE(request.getParameter("LMI_SYS_TRANS_DATE"));
			conf.setLMI_SYS_INVS_NO(request.getParameter("LMI_SYS_INVS_NO"));
			conf.setLMI_SYS_TRANS_NO(request.getParameter("LMI_SYS_TRANS_NO"));
			getLogger().debug("action \"/wm/result\" " + conf);

			if (StringUtils.isNotBlank(conf.getLMI_PAYMENT_NO()) && StringUtils.isNotBlank(conf.getLMI_PAYMENT_AMOUNT())) {
				Long paymentId = Long.parseLong(conf.getLMI_PAYMENT_NO());
//				Payment payment = paymentService.findOne(paymentId);
//				PaymentSystem paymentSystem = paymentSystemService.findOne(payment.getPaymentSystem().getId());
//				Order order=orderService.findOne(payment.getOrder().getId());
				PaymentCheckPojo checkPojo = paymentService.getPaymentCheckPojo(paymentId);
				if ("1".equalsIgnoreCase(conf.getLMI_PREREQUEST())) {
					if (checkPojo.getOrderStatus() == OrderStatus.UNPAID) {
						getLogger().debug("Processing 'paymentWM' pre-request.");
					}
				} else {
//					ProductProfile productProfile=productService.findProductProfile(order.getProductProfile().getId());
					String preparedString = StringUtils.join(new String[]{
							checkPojo.getPaymentSystemExternalId(), checkPojo.getPrice().toString(), conf.getLMI_PAYMENT_NO(),
							checkPojo.isPaymentIsTestMode() ? "1" : "0", conf.getLMI_SYS_INVS_NO(), conf.getLMI_SYS_TRANS_NO(),
							conf.getLMI_SYS_TRANS_DATE(), checkPojo.getPaymentSystemSecretWord(),
							conf.getLMI_PAYER_PURSE(), conf.getLMI_PAYER_WM()
					});
					String MD5string = PasswordUtils.md5(preparedString);
					boolean paymentSuccess = StringUtils.equalsIgnoreCase(conf.getLMI_HASH(), MD5string);
					if (paymentSuccess) {
						getLogger().debug("WM check passed. Payment [" + paymentId + "]. Internal String:" + preparedString + "; Internal MD5:" + MD5string + " External MD5:" + conf.getLMI_HASH());
						orderService.payment(paymentId, conf.getLMI_SYS_TRANS_NO(), LocalDateTime.parse(conf.getLMI_SYS_TRANS_DATE(), DateTimeFormat.forPattern("yyyyMMdd HH:mm:ss")), conf.getLMI_SYS_INVS_NO(), conf.getLMI_PAYER_PURSE());
					} else {
						getLogger().debug("WM check not passed. Payment [" + paymentId + "]. Internal String:" + preparedString + "; Internal MD5:" + MD5string + " External MD5:" + conf.getLMI_HASH());
						orderService.errorPayment(paymentId);
					}
				}
			}
		} catch (Exception e) {
			getLogger().error(e.getMessage(), e);
		}
	}

	/**
	 * page to success
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/wm/success", method = RequestMethod.POST)
	public void wmSuccess(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PaymentWMFormPojo conf = new PaymentWMFormPojo();
		conf.setLMI_SYS_TRANS_NO(request.getParameter("LMI_SYS_TRANS_NO"));
		conf.setLMI_SYS_TRANS_DATE(request.getParameter("LMI_SYS_TRANS_DATE"));
		conf.setLMI_LANG(request.getParameter("LMI_LANG"));
		conf.setLMI_SYS_INVS_NO(request.getParameter("LMI_SYS_INVS_NO"));
		conf.setLMI_PAYMENT_NO(request.getParameter("LMI_PAYMENT_NO"));
		getLogger().debug("action \"/wm/success\" " + conf);

		response.sendRedirect("../index.html#payment_history");
	}

	/**
	 * page to fail
	 *
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/wm/fail")
	public void wmFail(HttpServletRequest request, HttpServletResponse response) throws IOException {
		PaymentWMFormPojo conf = new PaymentWMFormPojo();
		conf.setLMI_SYS_TRANS_NO(request.getParameter("LMI_SYS_TRANS_NO"));
		conf.setLMI_SYS_TRANS_DATE(request.getParameter("LMI_SYS_TRANS_DATE"));
		conf.setLMI_LANG(request.getParameter("LMI_LANG"));
		conf.setLMI_SYS_INVS_NO(request.getParameter("LMI_SYS_INVS_NO"));
		conf.setLMI_PAYMENT_NO(request.getParameter("LMI_PAYMENT_NO"));
		getLogger().debug("action \"/wm/fail\"" + conf);

		response.sendRedirect("../index.html#order");
	}
}
