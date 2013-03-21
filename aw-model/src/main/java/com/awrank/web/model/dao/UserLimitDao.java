package com.awrank.web.model.dao;

import com.awrank.web.model.domain.UserLimit;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author Alex Polyakov
 */
public interface UserLimitDao extends CrudRepository<UserLimit, Long> {
	@Query("select r from UserLimit r where r.user.id = :userId and r.userLimitType='DAY' and r.startedDate <= now() and now() <= r.endedDate ")
	public UserLimit findOneByUserIdAndTypeDayAndNow(@Param("userId") Long userId);

	@Query("select r from UserLimit r where r.order.id = :orderId and r.userLimitType='MONTH' and r.startedDate <= now() and now() <= r.endedDate ")
	public UserLimit findOneByOrderIdAndTypeMonthAndNow(@Param("orderId") Long orderId);
}
