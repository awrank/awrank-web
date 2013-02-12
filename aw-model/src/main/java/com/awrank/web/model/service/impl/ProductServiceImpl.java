package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.ProductDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.domain.constant.ETariffType;
import com.awrank.web.model.service.ProductService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    public ProductDao productDao;



    @Override
    public void test() {
        Product tt = new Product();

        tt.setName("test");
        tt.setDescription("Test");
        tt.setProductType(ProductType.UNLIMITED);
        tt.setProductVisibility(ProductVisibility.ALL);


        ProductProfile productProfile = new ProductProfile();
        productProfile.setPrice(new BigDecimal(0.0));
        productProfile.setStartedDate(DateTime.now());
        productProfile.setProduct(tt);


//        tt.setTariffType(ETariffType.F);
//        tt.setCountDailyRequest(100);
//        tt.setCountMonthlyRequest(1000);

        productDao.save(tt);


        Product p = productDao.findOne(1L);

        System.out.print(p.getCreatedDate());
    }
}
