package com.awrank.web.model.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import com.awrank.web.model.domain.UserEmailActivation;
/**
 * 
 * @author Olga Korokhina
 */
public interface UserEmailActivationDao extends PagingAndSortingRepository<UserEmailActivation, Long> {
	 
	@Query("select a from UserEmailActivation a where a.code = :code")
	public UserEmailActivation select(@Param("code") String code);
	

}
