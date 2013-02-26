package com.awrank.web.model.dao;

import com.awrank.web.model.domain.EntryPoint;
import java.util.List;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.EntryPointType;
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
	 List<EntryPoint> selectActiveByType(@Param("user") User user, @Param("type") EntryPointType type);
	 
	 @Query("select e from EntryPoint e where e.user = :user and e.type = :type and e.password = :password and e.endedDate is NULL")
	 List<EntryPoint> selectActiveByTypeAndPassword(@Param("user") User user, @Param("type") EntryPointType type, @Param("password") String password);
	 
}