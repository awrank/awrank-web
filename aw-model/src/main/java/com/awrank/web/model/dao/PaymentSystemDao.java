package com.awrank.web.model.dao;

import com.awrank.web.model.domain.PaymentSystem;
import com.awrank.web.model.domain.constant.PaymentSystemConst;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * User: a_polyakov
 */
public interface PaymentSystemDao extends CrudRepository<PaymentSystem, Long> {
	@Query("select o from PaymentSystem o where o." + PaymentSystemConst.H_EXTERNAL_ID + " = :externalId")
	public List<PaymentSystem> findAllByExternalId(@Param("externalId") String externalId);
}
