package com.awrank.web.model.dao;

import com.awrank.web.model.domain.Payout;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * The {@code PayoutDao} is a data-centric service for the {@link Payout} entity.
 *
 * It provides the basic methods to get/delete a {@link Payout} instance
 * plus some methods to perform searches (extends {@link PagingAndSortingRepository}).
 *
 * @author Eugene Solomka
 */
public interface PayoutDao extends PagingAndSortingRepository<Payout, Long> {
}