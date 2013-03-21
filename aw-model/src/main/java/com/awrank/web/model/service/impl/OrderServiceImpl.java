package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.OrderDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.service.*;
import com.awrank.web.model.service.impl.pojos.OrderCreateResultPojo;
import com.awrank.web.model.service.impl.pojos.PaymentWMFormPojo;
import com.awrank.web.model.utils.user.PasswordUtils;
import com.twocheckout.TwocheckoutNotification;
import com.twocheckout.TwocheckoutReturn;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class OrderServiceImpl extends AbstractServiceImpl implements OrderService {
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private EntryHistoryService entryHistoryService;
	@Autowired
	private PaymentSystemService paymentSystemService;
	@Autowired
	private ProductService productService;
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private DictionaryService dictionaryService;
	@Autowired
	private DiaryService diaryService;
	@Autowired
	private UserLimitService userLimitService;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public OrderCreateResultPojo create(Long entryHistoryId, Long paymentSystemId, Long productProfileId) {
		getLogger().debug("create begin entryHistoryId:" + entryHistoryId + ", paymentSystemId:" + paymentSystemId + ", productProfileId:" + productProfileId);
		EntryHistory entryHistory = entryHistoryService.findOneById(entryHistoryId);
		User user = entryHistory.getUser();
		PaymentSystem paymentSystem = paymentSystemService.findOne(paymentSystemId);
		ProductProfile productProfile = productService.findProductProfile(productProfileId);

		Order order = new Order();
		order.setUser(user);
		order.setProductProfile(productProfile);
		order.setStatus(OrderStatus.UNPAID);
		orderDao.save(order);
		getLogger().debug("create new oredr id:" + order.getId());

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
		getLogger().debug("create new payment id:" + payment.getId());

		Diary diary = new Diary();
		diary.setUser(user);
		diary.setEntryHistory(entryHistory);//cannot be null
		diary.setOldValue(null);
		diary.setNewValue("new payment tariff:" + productProfile.getProduct().getName() + " price:" + productProfile.getPrice());
		diary.setEvent(DiaryEvent.START_PAY);
		diaryService.save(diary);
		getLogger().debug("create new diary id:" + diary.getId());

		String text = dictionaryService.getTextByLanguageAndCode(user.getLanguage(), productProfile.getProduct().getName());
		OrderCreateResultPojo result = new OrderCreateResultPojo(paymentSystem.getSystemUrl(), payment.getAmount(), text, payment.getId(), paymentSystem.getExternalId(), paymentSystem.isTestMode());
		getLogger().debug("create end");
		return result;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean paymentMW(PaymentWMFormPojo confirmation) {
		boolean result = true;
		if (StringUtils.isNotBlank(confirmation.getLMI_PAYMENT_NO()) && StringUtils.isNotBlank(confirmation.getLMI_PAYMENT_AMOUNT())) {
			result = false;
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
						result = true;
						payment(payment, confirmation.getLMI_SYS_TRANS_NO(), LocalDateTime.parse(confirmation.getLMI_SYS_TRANS_DATE(), DateTimeFormat.forPattern("yyyyMMdd HH:mm:ss")), confirmation.getLMI_SYS_INVS_NO(), confirmation.getLMI_PAYER_PURSE());
						getLogger().debug("WM check passed. Amount:" + amountWM + "; Internal String:" + preparedString + "; Internal MD5:" + MD5string
								+ "; External MD5:" + confirmation.getLMI_HASH());
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

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean paymentTwoCheckOut(String merchant_order_id, String sid, String total, String order_number, String key) {

		Long paymentId = Long.parseLong(merchant_order_id);
		Payment payment = paymentService.findOne(paymentId);
		PaymentSystem paymentSystem = payment.getPaymentSystem();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("sid", sid);
		params.put("total", total);
		params.put("order_number", order_number);
		params.put("key", key);

		Boolean result = TwocheckoutReturn.check(params, payment.getPaymentSystem().getSecretWord());
		if (result) {
			try {
				// https://github.com/2Checkout/2checkout-java/wiki/Sale_Retrieve
//				Sale sale = TwocheckoutSale.retrieve(order_number, paymentSystem.getUsername(), paymentSystem.getPassword());
				// TODO sale not field from json
//				sale.getDatePlaced()
//				sale.getCustomer().getCustomerId
//				transaction id
				// TODO
				payment(payment, order_number, new LocalDateTime(), order_number, "unknown");
			} catch (Exception e) {
				getLogger().error(e.getMessage(), e);
			}
		}
		return result;
	}

	// TODO
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void eventTwoCheckOut(Map<String, String> params) {
		//TODO
		String secret_word = "secretword";
		Boolean result = TwocheckoutNotification.check(params, secret_word);

		if (result) {
			// request is not a fake
			String message_type = params.get("message_type");
			if (message_type.equals("ORDER_CREATED")) {

			} else if (message_type.equals("FRAUD_STATUS_CHANGED")) {
				String fraud_status = params.get("fraud_status");
				if (fraud_status.equals("pass")) {

				}
			} else if (message_type.equals("INVOICE_STATUS_CHANGED")) {

			} else if (message_type.equals("REFUND_ISSUED")) {

			} else if (message_type.equals("RECURRING_INSTALLMENT_SUCCESS")) {

			} else if (message_type.equals("RECURRING_INSTALLMENT_FAILED")) {

			} else if (message_type.equals("RECURRING_STOPPED")) {

			} else if (message_type.equals("RECURRING_COMPLETE")) {

			} else if (message_type.equals("RECURRING_RESTARTED")) {

			}
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Order findOneStatusPaidAndLimitDayAndNow(Long userId) {
		Order order = orderDao.findOneStatusPaidAndLimitDayAndNow(userId);
		return order;
	}

	private void payment(Payment payment, String transactionRef, LocalDateTime transactionDate, String paymentRef, String payerRef) {
		getLogger().debug("payment begin");
		final Order order = payment.getOrder();
		final User user = order.getUser();
		final ProductProfile productProfile = order.getProductProfile();

		// update payment
		payment.setStatus(PaymentStatus.ALLOCATED);
		payment.setTransactionRef(transactionRef);
		payment.setTransactionDate(transactionDate);
		payment.setPaymentRef(paymentRef);
		payment.setPayerRef(payerRef);
		paymentService.save(payment);
		getLogger().debug("payment payment allocation");

		// update order
		order.setStatus(OrderStatus.PAID);
		// data end
		LocalDateTime startDate = orderDao.findLastEndedDateByUserId(user.getId());
		if (startDate == null)
			startDate = new LocalDateTime();
		LocalDateTime endedDate = startDate.plus(Period.days(productProfile.getProduct().getPeriodInDays()));
		order.setEndedDate(endedDate);
		orderDao.save(order);
		getLogger().debug("payment order paid");

		// create limit
		Integer countMonthlyRequest = productProfile.getProduct().getCountMonthlyRequest();
		if (countMonthlyRequest == null || countMonthlyRequest == 0) {
			// unlimit month
			UserLimit userLimit = new UserLimit();
			userLimit.setUser(user);
			userLimit.setOrder(order);
			userLimit.setAvailableRequests(Integer.MAX_VALUE);
			userLimit.setStartedDate(startDate);
			userLimit.setEndedDate(endedDate);
			userLimit.setUserLimitType(UserLimitType.PERIOD);
			userLimit.setLimitMonth(null);
			userLimitService.save(userLimit);
			getLogger().debug("payment new userLimit from " + startDate + " to " + endedDate);
		} else {
			// create limits on month
			while (startDate.compareTo(endedDate) < 0) {
				LocalDateTime currentEndedDate = startDate.plus(Period.days(30));
				if (currentEndedDate.compareTo(endedDate) > 0)
					currentEndedDate = endedDate;
				UserLimit userLimit = new UserLimit();
				userLimit.setUser(user);
				userLimit.setOrder(order);
				userLimit.setAvailableRequests(Integer.MAX_VALUE);
				userLimit.setStartedDate(startDate);
				userLimit.setEndedDate(currentEndedDate);
				userLimit.setUserLimitType(UserLimitType.MONTH);
				userLimit.setLimitMonth(null);
				userLimitService.save(userLimit);
				getLogger().debug("payment new userLimit from " + startDate + " to " + endedDate);
				startDate = currentEndedDate;
			}
		}

		// add history record
		final EntryHistory entryHistory = entryHistoryService.getLatestEntryForUser(user);
		Diary diary = new Diary();
		diary.setUser(user);
		diary.setEntryHistory(entryHistory);//cannot be null
		diary.setOldValue(null);
		diary.setNewValue("payment success tariff:" + productProfile.getProduct().getName() + " price:" + productProfile.getPrice());
		diary.setEvent(DiaryEvent.SUCCESSFULLY_PAID);
		diaryService.save(diary);
		getLogger().debug("create new diary id:" + diary.getId());
		getLogger().debug("payment end");
	}
}
