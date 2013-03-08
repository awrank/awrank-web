package com.awrank.web.backend.controller.rest.product;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.backend.exception.ForbiddenException;
import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.dao.pojos.PricingFormProductProfilePojo;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductController extends AbstractController {
	@Autowired
	private ProductService productService;

	/**
	 * Returns paginated list of dictionary entries
	 */
//	@PreAuthorize("hasRole('ROLE_USER')") not work!!!
//	@PreAuthorize("permitAll")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public
	@ResponseBody
	List<PricingFormProductProfilePojo> selectAvailable() throws UnauthorizedException, ForbiddenException {
		checkHasAnyRole(Role.ROLE_ADMIN, Role.ROLE_USER);
		return productService.selectAvailable();
	}
}
