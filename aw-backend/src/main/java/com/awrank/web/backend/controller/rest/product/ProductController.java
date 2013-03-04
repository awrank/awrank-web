package com.awrank.web.backend.controller.rest.product;

import com.awrank.web.model.dao.pojos.PricingFormProductProfilePojo;
import com.awrank.web.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * User: a_polyakov
 */
@Controller
@RequestMapping(value = "/rest/product")
public class ProductController {
	@Autowired
	private ProductService productService;

	/**
	 * Returns paginated list of dictionary entries
	 */
	@PreAuthorize("permitAll")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public
	@ResponseBody
	List<PricingFormProductProfilePojo> selectAvailable() {
		return productService.selectAvailable();
	}
}
