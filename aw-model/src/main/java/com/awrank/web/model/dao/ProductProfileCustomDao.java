package com.awrank.web.model.dao;

import com.awrank.web.model.dao.pojos.PricingFormProductProfilePojo;

import java.util.List;

/**
 * @author Alex Polyakov
 */
public interface ProductProfileCustomDao {
	public List<PricingFormProductProfilePojo> selectAvailable();
}
