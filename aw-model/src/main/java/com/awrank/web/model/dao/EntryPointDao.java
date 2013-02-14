package com.awrank.web.model.dao;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.User;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * The {@code EntryPointDao} is a data-centric service for the {@link EntryPoint} entity.
 * <p/>
 * It provides the basic methods to get/delete a {@link EntryPoint} instance
 * plus some methods to perform searches (extends {@link CrudRepository}).
 *
 * @author Eugene Solomka
 */
public interface EntryPointDao extends CrudRepository<EntryPoint, Long> {
	
	//org.hibernate.QueryException: could not resolve property: user_id of: com.awrank.web.model.domain.EntryPoint [select e from com.awrank.web.model.domain.EntryPoint e where e.user_id = :user_id]
	/*
	 @Query("select e from EntryPoint e where e.user_id = :user_id")
	 EntryPoint select(@Param("user_id") User user_id);
	 */
}