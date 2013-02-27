package com.awrank.web.model.dao;

import com.awrank.web.model.dao.pojos.PricingFormProductProfilePojo;

import java.util.List;

/**
 * User: a_polyakov
 */
public interface ProductProfileCustomDao {
	public List<PricingFormProductProfilePojo> selectAvailable();
}
