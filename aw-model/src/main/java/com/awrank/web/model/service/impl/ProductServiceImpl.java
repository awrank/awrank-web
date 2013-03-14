package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.ProductDao;
import com.awrank.web.model.dao.ProductProfileCustomDao;
import com.awrank.web.model.dao.ProductProfileDao;
import com.awrank.web.model.dao.pojos.PricingFormProductProfilePojo;
import com.awrank.web.model.domain.ProductProfile;
import com.awrank.web.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl extends AbstractServiceImpl implements ProductService {
	@Autowired
	public ProductDao productDao;
	@Autowired
	public ProductProfileDao productProfileDao;
	@Autowired
	public ProductProfileCustomDao productProfileCustomDao;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ProductProfile findProductProfile(Long id) {
		return productProfileDao.findOne(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PricingFormProductProfilePojo> selectAvailable() {
		return productProfileCustomDao.selectAvailable();
	}
}
