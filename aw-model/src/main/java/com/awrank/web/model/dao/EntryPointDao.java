package com.awrank.web.model.dao;

import com.awrank.web.model.domain.EntryPoint;
import org.springframework.data.repository.CrudRepository;

/**
 * The OrderDao is a data-centric service for the {@link EntryPoint} entity.
 *
 * It provides the basic methods to get/delete a {@link EntryPoint} instance
 * plus some methods to perform searches (extends {@link CrudRepository}).
 *
 * @author Eugene Solomka
 */
public interface EntryPointDao extends CrudRepository<EntryPoint, Long> {
}