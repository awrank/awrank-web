package com.awrank.web.backend.init;

import com.awrank.web.backend.init.dictionary.InitDictionary;
import com.awrank.web.backend.init.product.InitProduct;
import com.awrank.web.backend.init.user.InitUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * User: a_polyakov
 */
@Controller
public class InitAll {

	@Autowired
	private InitDictionary initDictionary;
	@Autowired
	private InitUser initUser;
	@Autowired
	private InitProduct initProduct;

	//	@RequestMapping("/init")
	@PostConstruct
	public void init() {
		initUser.initAnonymous();
		initUser.initRegularUser();
		initUser.initAdmin();
		initDictionary.init();
		initProduct.init();
	}
}
