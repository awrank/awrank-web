package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.UserLimitDao;
import com.awrank.web.model.domain.Order;
import com.awrank.web.model.domain.UserLimit;
import com.awrank.web.model.domain.UserLimitType;
import com.awrank.web.model.service.OrderService;
import com.awrank.web.model.service.UserLimitService;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Alex Polyakov
 */
@Service
public class UserLimitServiceImpl extends AbstractServiceImpl implements UserLimitService {
	@Autowired
	private UserLimitDao userLimitDao;
	@Autowired
	private OrderService orderService;

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void save(UserLimit userLimit) {
		userLimitDao.save(userLimit);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void createDayLimit(Long userId) {
		UserLimit userLimit = userLimitDao.findOneByUserIdAndTypeDayAndNow(userId);
		if (userLimit == null) {
			Order order = orderService.findOneStatusPaidAndLimitDayAndNow(userId);
			if (order != null) {
				userLimit = new UserLimit();
				userLimit.setUser(order.getUser());
				userLimit.setOrder(order);
				userLimit.setAvailableRequests(order.getProductProfile().getProduct().getCountDailyRequest());

				LocalDateTime startDate = LocalDateTime.now();
//				startDate=startDate.minusMillis(startDate.getMillisOfDay());
				startDate = startDate.withTime(0, 0, 0, 0);
				userLimit.setStartedDate(startDate);
				// 86399999=23:59:59 999
//				LocalDateTime endedDate= startDate.plusMillis(86399999);
				LocalDateTime endedDate = startDate.withTime(23, 59, 59, 999);
				userLimit.setEndedDate(endedDate);
				userLimit.setUserLimitType(UserLimitType.DAY);
				Integer countMonthlyRequest = order.getProductProfile().getProduct().getCountMonthlyRequest();
				if (countMonthlyRequest != null && countMonthlyRequest > 0) {
					UserLimit limitMonth = userLimitDao.findOneByOrderIdAndTypeMonthAndNow(order.getId());
					userLimit.setLimitMonth(limitMonth);
				} else {
					userLimit.setLimitMonth(null);
				}
				userLimitDao.save(userLimit);
				getLogger().debug("createDayLimit new userLimit from " + startDate + " to " + endedDate + " request " + userLimit.getAvailableRequests());
			}
		}
	}
}
