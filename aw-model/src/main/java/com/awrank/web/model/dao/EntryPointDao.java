package com.awrank.web.model.dao;

import com.awrank.web.model.domain.EntryPoint;
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
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 * @author Alex Polyakov
 */
public interface EntryPointDao extends CrudRepository<EntryPoint, Long> {

	@Deprecated
	@Query("select e from EntryPoint e where e.type = :type and e.uid = :uid and e.verifiedDate is not null and e.endedDate is NULL")
	EntryPoint findActiveByTypeAndUid(@Param("type") EntryPointType type, @Param("uid") String uid);

	// Olga: I removed " and e.verifiedDate is not null "  from query -
	// with this condition newly-registered user with not verified email can not be logged in!!!
	@Query("select e from EntryPoint e where e.uid = :uid and e.endedDate is NULL")
	EntryPoint findActiveByUid(@Param("uid") String uid);

}