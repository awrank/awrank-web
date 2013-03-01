package com.awrank.web.model.dao;

import com.awrank.web.model.domain.StateChangeToken;
import com.awrank.web.model.enums.StateChangeTokenType;

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
public interface StateChangeTokenDao extends PagingAndSortingRepository<StateChangeToken, Long> {

    @Query("select a from StateChangeToken a where a.token = :token and a.type = :type")
    StateChangeToken select(@Param("token") String token, @Param("type") StateChangeTokenType type);

}
