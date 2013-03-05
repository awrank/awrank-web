package com.awrank.web.backend.init;

import com.awrank.web.backend.init.dictionary.InitDictionary;
import com.awrank.web.backend.init.product.InitProduct;
import com.awrank.web.backend.init.user.InitUser;
import com.awrank.web.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

/**
 * User: a_polyakov
 */
@Controller
public class InitAll {

//	@Autowired
//	AuditorAwareImpl auditorAware;

	@Autowired
	private InitDictionary initDictionary;
	@Autowired
	private InitUser initUser;
	@Autowired
	private InitProduct initProduct;
	@Autowired
	private InitPaymentSystem initPaymentSystem;

	//	@RequestMapping("/init")
	@PostConstruct
	public void init() {
		User anonymous = initUser.initAnonymous();

//		SecurityContextHolder.getContext().setAuthentication(new TestingAuthenticationToken());
//		auditorAware.setAuditor(anonymous);
		initUser.initRegularUser();
		initUser.initAdmin();
		initDictionary.init();
		initProduct.init();
		initPaymentSystem.init();
	}
}
