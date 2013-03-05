package com.awrank.web.model.service;

import com.awrank.web.model.dao.pojos.PricingFormProductProfilePojo;

import java.util.List;

public interface ProductService extends AbstractService {
	public List<PricingFormProductProfilePojo> selectAvailable();
}
