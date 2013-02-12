package com.awrank.web.model.dao;

import com.awrank.web.model.domain.Payment;
import org.springframework.data.repository.CrudRepository;

/**
 * The ProductDao is a data-centric service for the {@link Payment} entity.
 *
 * It provides the basic methods to get/delete a {@link Payment} instance
 * plus some methods to perform searches (extends {@link CrudRepository}).
 *
 * @author Eugene Solomka
 */
public interface PaymentDao extends CrudRepository<Payment, Long> {
}