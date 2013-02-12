package com.awrank.web.model.dao;

import com.awrank.web.model.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * The OrderDao is a data-centric service for the {@link Order} entity.
 *
 * It provides the basic methods to get/delete a {@link Order} instance
 * plus some methods to perform searches (extends {@link PagingAndSortingRepository}).
 *
 * @author Eugene Solomka
 */
public interface OrderDao extends PagingAndSortingRepository<Order, Long> {
    //Page<Order> findByUser (User user, Pageable pageable);
}
