package com.awrank.web.model.service;

import com.awrank.web.model.service.impl.pojos.PricingFormProductProfilePojo;

import java.util.List;

public interface ProductService {
	public List<PricingFormProductProfilePojo> selectAvailable();
}
