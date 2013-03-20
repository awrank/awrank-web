package com.awrank.web.model.dao;

import com.awrank.web.model.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * The {@code UserDao} is a data-centric service for the {@link User} entity.
 * <p/>
 * It provides the basic methods to get/delete a {@link User} instance
 * plus some methods to perform searches (extends {@link PagingAndSortingRepository}).
 *
 * @author Eugene Solomka
 */
public interface UserDao extends PagingAndSortingRepository<User, Long> {
	/**
	 * Finds user by email address
	 */
	@Query("select u from User u where u.email = :email")
	User findByEmail(@Param("email") String email);

	@Query("select u from User u where u.email = :email")
	Page<User> pFindByEmail(@Param("email") String email, Pageable pageable);

	@Query("select u from User u where u.apiKey = :api_key")
	User findByIPIKey(@Param("api_key") String api_key);
}
