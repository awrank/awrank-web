package com.awrank.web.model.dao;

import com.awrank.web.model.domain.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * The {@code ProductDao} is a data-centric service for the {@link Product} entity.
 * <p/>
 * It provides the basic methods to get/delete a {@link Product} instance
 * plus some methods to perform searches (extends {@link CrudRepository}).
 *
 * @author Eugene Solomka
 */
public interface ProductDao extends CrudRepository<Product, Long> {
	@Query("select o from Product o where o.name = :name")
	Product findByName(@Param("name") String name);
}
