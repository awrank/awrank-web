package com.awrank.web.model.dao;

import java.util.List;

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
    List<User> findByEmail(@Param("email") String email);
    
    @Query("select u from User u where u.id = :id")
    List<User> findById(@Param("id") Long id);

    @Query("select u from User u where u.apiKey = :api_key")
    List<User> findByIPIKey(@Param("api_key") String api_key);
}
