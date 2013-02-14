package com.awrank.web.model.dao;

import com.awrank.web.model.domain.Order;
import com.awrank.web.model.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * The OrderDao is a data-centric service for the {@link Order} entity.
 *
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
    public Page<Order> findByUser (@Param("user") User user, Pageable pageable);
}
