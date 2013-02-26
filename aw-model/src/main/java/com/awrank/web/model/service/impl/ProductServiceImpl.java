package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.ProductDao;
import com.awrank.web.model.dao.ProductProfileCustomDao;
import com.awrank.web.model.dao.ProductProfileDao;
import com.awrank.web.model.service.ProductService;
import com.awrank.web.model.service.impl.pojos.PricingFormProductProfilePojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired
	public ProductDao productDao;
	@Autowired
	public ProductProfileDao productProfileDao;
	@Autowired
	public ProductProfileCustomDao productProfileCustomDao;

	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<PricingFormProductProfilePojo> selectAvailable() {
		return productProfileCustomDao.selectAvailable();
	}
}
