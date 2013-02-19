package com.awrank.web.model.dao;

import com.awrank.web.model.domain.UserRole;
import java.util.List;
import com.awrank.web.model.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

/**
 * The {@code UserRoleDao} is a data-centric service for the {@link UserRole} entity.
 * <p/>
 * It provides the basic methods to get/delete a {@link UserRole} instance
 * plus some methods to perform searches (extends {@link CrudRepository}).
 *
 * @author Olga Korokhina
 */
public interface UserRoleDao extends CrudRepository<UserRole, Long> {
	
	 @Query("select r from UserRole r where r.user = :user")
	 List<UserRole> select(@Param("user") User user);
	 
}