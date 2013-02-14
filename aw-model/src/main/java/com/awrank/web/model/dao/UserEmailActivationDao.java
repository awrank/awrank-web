package com.awrank.web.model.dao;

import com.awrank.web.model.domain.UserEmailActivation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * The {@code UserEmailActivationDao} is a data-centric service for the {@link UserEmailActivation} entity.
 * <p/>
 * It provides the basic methods to get/delete a {@link UserEmailActivation} instance
 * plus some methods to perform searches (extends {@link PagingAndSortingRepository}).
 *
 * @author Olga Korokhina
 */
public interface UserEmailActivationDao extends PagingAndSortingRepository<UserEmailActivation, Long> {

    @Query("select a from UserEmailActivation a where a.code = :code")
    UserEmailActivation select(@Param("code") String code);


}
