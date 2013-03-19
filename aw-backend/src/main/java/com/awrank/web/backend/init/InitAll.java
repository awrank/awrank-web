package com.awrank.web.backend.init;

import com.awrank.web.backend.init.dictionary.InitDictionary;
import com.awrank.web.backend.init.dictionary.InitISO3166;
import com.awrank.web.backend.init.product.InitProduct;
import com.awrank.web.backend.init.user.InitUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * @author Alex Polyakov
 */
@Controller
public class InitAll {

	@Autowired
	private InitDictionary initDictionary;
	@Autowired
	private InitISO3166 initISO3166;
	@Autowired
	private InitUser initUser;
	@Autowired
	private InitProduct initProduct;
	@Autowired
	private InitPaymentSystem initPaymentSystem;

	//	@RequestMapping("/init")
	@PostConstruct
	public void init() {
//		initUser.initAnonymous();
		initUser.initAdmin();
		initUser.initRegularUser();
		initDictionary.init();
		initISO3166.init();
		initProduct.init();
		initPaymentSystem.init();
	}
}
