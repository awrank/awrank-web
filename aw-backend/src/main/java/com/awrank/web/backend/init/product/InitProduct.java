package com.awrank.web.backend.init.product;

import com.awrank.web.model.dao.ProductDao;
import com.awrank.web.model.dao.ProductProfileDao;
import com.awrank.web.model.domain.Product;
import com.awrank.web.model.domain.ProductProfile;
import com.awrank.web.model.domain.ProductType;
import com.awrank.web.model.domain.ProductVisibility;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * todo: Description
 *
 * @author Alex Polyakov
 */
@Service
public class InitProduct {

	@Autowired
	private ProductDao productDao;

	@Autowired
	private ProductProfileDao productProfileDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void init() {
		Product product = productDao.findByName("TARIFF_STARTED_NAME");
		if (product == null) {
			product = new Product();
			product.setName("TARIFF_STARTED_NAME");
			product.setDescription("TARIFF_STARTED_DESCRIPTION");
			product.setProductType(ProductType.MONTHLY);
			product.setProductVisibility(ProductVisibility.ALL);
			product.setPeriodInDays(30);
			product.setCountDailyRequest(10);
			product.setCountMonthlyRequest(0);
			product.setActive(Boolean.TRUE);

//            product.setCreatedDate(DateTime.now());
//            product.setLastModifiedDate(product.getCreatedDate());

			productDao.save(product);

			ProductProfile profile = new ProductProfile();
			profile.setProduct(product);
			profile.setPrice(new BigDecimal(9.99));
			profile.setDiscount(BigDecimal.ZERO);
			profile.setStartedDate(LocalDateTime.now());
			profile.setEndedDate(new LocalDateTime(2099, 12, 31, 23, 59, 59, 999));

//            profile.setCreatedDate(DateTime.now());
//            profile.setLastModifiedDate(profile.getCreatedDate());

			productProfileDao.save(profile);
		}

		product = productDao.findByName("TARIFF_BASIC_NAME");
		if (product == null) {
			product = new Product();
			product.setName("TARIFF_BASIC_NAME");
			product.setDescription("TARIFF_BASIC_DESCRIPTION");
			product.setProductType(ProductType.MONTHLY);
			product.setProductVisibility(ProductVisibility.ALL);
			product.setPeriodInDays(30);
			product.setCountDailyRequest(100);
			product.setCountMonthlyRequest(0);
			product.setActive(Boolean.TRUE);

//            product.setCreatedDate(DateTime.now());
//            product.setLastModifiedDate(product.getCreatedDate());

			productDao.save(product);

			ProductProfile profile = new ProductProfile();
			profile.setProduct(product);
			profile.setPrice(new BigDecimal(14.99));
			profile.setDiscount(BigDecimal.ZERO);
			profile.setStartedDate(LocalDateTime.now());
			profile.setEndedDate(new LocalDateTime(2099, 12, 31, 23, 59, 59, 999));

//            profile.setCreatedDate(DateTime.now());
//            profile.setLastModifiedDate(profile.getCreatedDate());

			productProfileDao.save(profile);
		}

		product = productDao.findByName("TARIFF_PRO_NAME");
		if (product == null) {
			product = new Product();
			product.setName("TARIFF_PRO_NAME");
			product.setDescription("TARIFF_PRO_DESCRIPTION");
			product.setProductType(ProductType.MONTHLY);
			product.setProductVisibility(ProductVisibility.ALL);
			product.setPeriodInDays(30);
			product.setCountDailyRequest(1000);
			product.setCountMonthlyRequest(0);
			product.setActive(Boolean.TRUE);

//            product.setCreatedDate(DateTime.now());
//            product.setLastModifiedDate(product.getCreatedDate());

			productDao.save(product);

			ProductProfile profile = new ProductProfile();
			profile.setProduct(product);
			profile.setPrice(new BigDecimal(24.99));
			profile.setDiscount(BigDecimal.ZERO);
			profile.setStartedDate(LocalDateTime.now());
			profile.setEndedDate(new LocalDateTime(2099, 12, 31, 23, 59, 59, 999));

//            profile.setCreatedDate(DateTime.now());
//            profile.setLastModifiedDate(profile.getCreatedDate());

			productProfileDao.save(profile);
		}
	}
}
