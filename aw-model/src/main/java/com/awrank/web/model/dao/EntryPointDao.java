package com.awrank.web.model.dao;

import com.awrank.web.model.domain.EntryPoint;
import java.util.List;
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
	
	 @Query("select e from EntryPoint e where e.user = :user")
	 List<EntryPoint> select(@Param("user") User user);
	 
	 @Query("select e from EntryPoint e where e.user = :user and e.type = :type and e.endedDate is NULL")
	 //@Query("select e from EntryPoint e where e.user = :user and e.type = :type")
	 List<EntryPoint> selectActiveByType(@Param("user") User user, @Param("type") String type);
}