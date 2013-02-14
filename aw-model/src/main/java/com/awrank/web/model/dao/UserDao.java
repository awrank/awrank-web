package com.awrank.web.model.dao;

import com.awrank.web.model.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * The {@code UserDao} is a data-centric service for the {@link User} entity.
 *
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

}
