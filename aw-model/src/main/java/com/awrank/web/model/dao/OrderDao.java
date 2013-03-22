package com.awrank.web.model.dao;

import com.awrank.web.model.domain.Order;
import com.awrank.web.model.domain.User;
import org.joda.time.LocalDateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * The {@code OrderDao} is a data-centric service for the {@link Order} entity.
 * <p/>
 * It provides the basic methods to get/delete a {@link Order} instance
 * plus some methods to perform searches (extends {@link PagingAndSortingRepository}).
 *
 * @author Eugene Solomka
 */
public interface OrderDao extends PagingAndSortingRepository<Order, Long> {
	/**
	 * Finds user's orders
	 */
	@Query("select o from Order o where o.user = :user")
	Page<Order> findByUser(@Param("user") User user, Pageable pageable);

	/**
	 * find ended date last paid order by user
	 *
	 * @param userId
	 * @return
	 */
	@Query("select max(o.endedDate) from Order o where o.user.id = :userId")
	public LocalDateTime findLastEndedDateByUserId(@Param("userId") Long userId);

	/**
	 * find paid order which today is the limit on the number of requests per day
	 *
	 * @param userId
	 * @return
	 */
	@Query("select o from Order o where o.user.id = :userId and o.status = 'PAID' and o.productProfile.product.countDailyRequest > 0 and now() <= o.endedDate order by o.createdDate desc limit 1")
	public Order findOneStatusPaidAndLimitDayAndNow(@Param("userId") Long userId);
}
