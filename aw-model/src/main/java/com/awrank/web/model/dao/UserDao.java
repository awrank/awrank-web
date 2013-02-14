package com.awrank.web.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.awrank.web.model.domain.User;

/**
 * User: a_polyakov
 * refactored by Olga following DictionaryDao way
 */
public interface UserDao extends PagingAndSortingRepository<User, Long> {
    
	 @Query("select u from User u where u.email = :email")
	    public User select(@Param("email") String email);	
    
}
