package com.awrank.web.model.service;

import com.awrank.web.model.dao.pojos.PricingFormProductProfilePojo;
import com.awrank.web.model.domain.ProductProfile;

import java.util.List;

public interface ProductService extends AbstractService {

	public ProductProfile findProductProfile(Long id);

	public List<PricingFormProductProfilePojo> selectAvailable();
}
