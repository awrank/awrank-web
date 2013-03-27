package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.OrderDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.service.*;
import com.awrank.web.model.service.impl.pojos.OrderCreateResultPojo;
import com.awrank.web.model.service.impl.pojos.PaymentWMFormPojo;
import com.awrank.web.model.utils.user.PasswordUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.Period;
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
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Order findOne(Long orderId) {
		return orderDao.findOne(orderId);
	}

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
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public Order findOneStatusPaidAndLimitDayAndNow(Long userId) {
		Order order = orderDao.findOneStatusPaidAndLimitDayAndNow(userId);
		return order;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void payment(Long paymentId, String transactionRef, LocalDateTime transactionDate, String paymentRef, String payerRef) {
		getLogger().debug("payment begin");
		final Payment payment = paymentService.findOne(paymentId);
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
				LocalDateTime currentEndedDate = startDate.plusDays(30);
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
		diary.setNewValue("payment success paymentId:"+payment.getId()+" tariff:" + productProfile.getProduct().getName() + " price:" + productProfile.getPrice());
		diary.setEvent(DiaryEvent.SUCCESSFULLY_PAID);
		diaryService.save(diary);
		getLogger().debug("create new diary id:" + diary.getId());
		getLogger().debug("payment end");
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void errorPayment(Long paymentId) {
		getLogger().debug("errorPayment begin");
		Payment payment = paymentService.findOne(paymentId);
		final Order order = payment.getOrder();
		final User user = order.getUser();
		final ProductProfile productProfile = order.getProductProfile();

		// add history record
		final EntryHistory entryHistory = entryHistoryService.getLatestEntryForUser(user);
		Diary diary = new Diary();
		diary.setUser(user);
		diary.setEntryHistory(entryHistory);//cannot be null
		diary.setOldValue(null);
		diary.setNewValue("payment error paymentId:"+payment.getId()+" tariff:" + productProfile.getProduct().getName() + " price:" + productProfile.getPrice());
		diary.setEvent(DiaryEvent.ERROR_PAID);
		diaryService.save(diary);
		getLogger().debug("create new diary id:" + diary.getId());
		getLogger().debug("errorPayment end");
	}
}
