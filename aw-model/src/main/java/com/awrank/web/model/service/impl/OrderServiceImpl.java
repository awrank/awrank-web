package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.OrderDao;
import com.awrank.web.model.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl extends AbstractServiceImpl implements OrderService {
	@Autowired
	public OrderDao orderDao;

}
