package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.ProductDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.service.ProductService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    public ProductDao productDao;
}
