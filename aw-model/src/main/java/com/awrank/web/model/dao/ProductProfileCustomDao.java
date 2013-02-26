package com.awrank.web.model.dao;

import com.awrank.web.model.service.impl.pojos.PricingFormProductProfilePojo;

import java.util.List;

/**
 * User: a_polyakov
 */
public interface ProductProfileCustomDao {
	public List<PricingFormProductProfilePojo> selectAvailable();
}
