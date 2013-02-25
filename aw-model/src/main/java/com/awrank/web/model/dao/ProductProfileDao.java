package com.awrank.web.model.dao;

import com.awrank.web.model.domain.ProductProfile;
import org.springframework.data.repository.CrudRepository;

/**
 * The {@code ProductProfileDao} is a data-centric service for the {@link com.awrank.web.model.domain.ProductProfile} entity.
 * <p/>
 * It provides the basic methods to get/delete a {@link com.awrank.web.model.domain.ProductProfile} instance
 * plus some methods to perform searches (extends {@link org.springframework.data.repository.CrudRepository}).
 *
 * @author Eugene Solomka
 */
public interface ProductProfileDao extends CrudRepository<ProductProfile, Long> {

}
