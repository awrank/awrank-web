package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.OrderDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.service.*;
import com.awrank.web.model.service.impl.pojos.OrderCreateResultPojo;
import com.awrank.web.model.service.impl.pojos.PaymentWMFormPojo;
import com.awrank.web.model.utils.user.PasswordUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class OrderServiceImpl extends AbstractServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private UserService userService;
	@Autowired
	private PaymentSystemService paymentSystemService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private DictionaryService dictionaryService;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public OrderCreateResultPojo create(Long userId, Long paymentSystemId, Long productProfileId) {
		User user = userService.findOne(userId);
		PaymentSystem paymentSystem = paymentSystemService.findOne(paymentSystemId);
		ProductProfile productProfile = productService.findProductProfile(productProfileId);

		Order order = new Order();
		order.setUser(user);
		order.setProductProfile(productProfile);
		order.setStatus(OrderStatus.UNPAID);
		orderDao.save(order);

		Payment payment = new Payment();
		payment.setOrder(order);
		payment.setPaymentSystem(paymentSystem);
		payment.setAmount(productProfile.getPrice());
		payment.setCurrency(productProfile.getCurrency());
		payment.setAmountCurrency(productProfile.getPrice());
		payment.setOriginalCurrency(productProfile.getCurrency());
		payment.setStatus(PaymentStatus.UNALLOCATED);
		payment.setTransactionRef(null);
		payment.setPaymentRef(null);
		payment.setPayerRef(null);
		//TODO
		payment.setDescription(null);
		paymentService.save(payment);

		String text = dictionaryService.getTextByLanguageAndCode(user.getLanguage(), productProfile.getProduct().getName());
		OrderCreateResultPojo result = new OrderCreateResultPojo(paymentSystem.getSystemUrl(), payment.getAmount(), text, payment.getId(), paymentSystem.getExternalId(), paymentSystem.isTestMode());
		return result;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean paymentMW(PaymentWMFormPojo confirmation) {
		boolean result = false;
		if (StringUtils.isNotBlank(confirmation.getLMI_PAYMENT_NO()) && StringUtils.isNotBlank(confirmation.getLMI_PAYMENT_AMOUNT())) {
			try {
				Long paymentId = Long.parseLong(confirmation.getLMI_PAYMENT_NO());
				Payment payment = paymentService.findOne(paymentId);
				if ("1".equalsIgnoreCase(confirmation.getLMI_PREREQUEST())) {
					if (payment.getOrder().isUnpaid()) {
						getLogger().debug("Processing 'paymentWM' pre-request.");
						result = true;
					}
				} else if (StringUtils.isNotBlank(confirmation.getLMI_HASH())) {
					String preparedString = StringUtils.join(new String[]{
							confirmation.getLMI_PAYEE_PURSE(), confirmation.getLMI_PAYMENT_AMOUNT(), confirmation.getLMI_PAYMENT_NO(),
							confirmation.getLMI_MODE(), confirmation.getLMI_SYS_INVS_NO(), confirmation.getLMI_SYS_TRANS_NO(),
							confirmation.getLMI_SYS_TRANS_DATE(), payment.getPaymentSystem().getSecretWord(),
							confirmation.getLMI_PAYER_PURSE(), confirmation.getLMI_PAYER_WM()
					});

					BigDecimal amountWM = new BigDecimal(confirmation.getLMI_PAYMENT_AMOUNT());
					String MD5string = PasswordUtils.md5(preparedString);

					if (StringUtils.equalsIgnoreCase(confirmation.getLMI_HASH(), MD5string) &&
							amountWM.compareTo(payment.getOrder().getProductProfile().getPrice()) == 0
							&& StringUtils.equals(confirmation.getLMI_PAYEE_PURSE(), payment.getPaymentSystem().getExternalId())
							&& StringUtils.equals(confirmation.getLMI_MODE(), payment.getPaymentSystem().isTestMode() ? "1" : "0")) {
						// history
//                        accountDiaryAccessor.createRecord(order.getOwner(), AccountDiaryEvent.WM_CHECK_PASSED,
//                                "WM check passed. Amount:" + amountWM + "; Internal String:" + preparedString + "; Internal MD5:" + MD5string
//                                        + "; External MD5:" + confirmation.getLMI_HASH());

						getLogger().debug("WM check passed. Amount:" + amountWM + "; Internal String:" + preparedString + "; Internal MD5:" + MD5string
								+ "; External MD5:" + confirmation.getLMI_HASH());

						// update payment
						payment.setStatus(PaymentStatus.ALLOCATED);
						payment.setTransactionRef(confirmation.getLMI_SYS_TRANS_NO());
						payment.setTransactionDate(LocalDateTime.parse(confirmation.getLMI_SYS_TRANS_DATE(), DateTimeFormat.forPattern("yyyyMMdd HH:mm:ss")));
						payment.setPaymentRef(confirmation.getLMI_SYS_INVS_NO());
						payment.setPayerRef(confirmation.getLMI_PAYER_PURSE());
						paymentService.save(payment);
						getLogger().debug("Processing 'paymentWM' request for payment allocation.");

						payment.getOrder().setStatus(OrderStatus.PAID);
						orderDao.save(payment.getOrder());

						// TODO Diary
//                        accountDiaryAccessor.createRecord(order.getOwner(), AccountDiaryEvent.SUCCESFULLY_PAID,
//                                " Amount:" + order.getAmount() +
//                                        " WMTransactionNO:" + confirmation.getLMI_SYS_TRANS_NO() +
//                                        " WMPaymentNO:" + confirmation.getLMI_SYS_INVS_NO() +
//                                        " WMInvoiceNO:" + confirmation.getLMI_SYS_INVS_NO() +
//                                        " OrderID:" + order.getId());
//
//						TODO TEST negative
						result = true;

					} else {
						// TODO Diary
//                        accountDiaryAccessor.createRecord(order.getOwner(), AccountDiaryEvent.WM_CHECK_FAILED,
//                                "WM check not passed. Order [" + order.getId() + "]. Internal String:" + preparedString + "; Internal MD5:" + MD5string +
//                                        " External MD5:" + confirmation.getLMI_HASH());
//
						getLogger().error("WM check not passed. Payment [" + payment.getId() + "]. Internal String:" + preparedString + "; Internal MD5:" + MD5string +
								" External MD5:" + confirmation.getLMI_HASH());
					}
				}
			} catch (Exception e) {
				getLogger().error(e.getMessage(), e);
			}
		}
		return result;
	}
}
