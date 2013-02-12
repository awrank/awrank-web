package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.PaymentDao;
import com.awrank.web.model.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    public PaymentDao paymentDao;
}
