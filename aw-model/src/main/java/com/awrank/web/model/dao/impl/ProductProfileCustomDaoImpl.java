package com.awrank.web.model.dao.impl;

import com.awrank.web.model.dao.ProductProfileCustomDao;
import com.awrank.web.model.dao.pojos.PricingFormProductProfilePojo;
import com.awrank.web.model.domain.constant.ProductProfileConst;
import com.awrank.web.model.utils.select.SelectUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: a_polyakov
 */
@Repository
public class ProductProfileCustomDaoImpl extends AbstractDaoImpl implements ProductProfileCustomDao {
	@Override
	public List<PricingFormProductProfilePojo> selectAvailable() {
		List<PricingFormProductProfilePojo> list = SelectUtils.getWrapperList(em, PricingFormProductProfilePojo.class,
				"where o." + ProductProfileConst.H_STARTED_DATE + " <= NOW()" +
						" and o." + ProductProfileConst.H_PRODUCT__PRODUCT_VISIBILITY + "='ALL'" +
						" and o." + ProductProfileConst.H_PRODUCT__IS_ACTIVE + " = true " +
						"order by o." + ProductProfileConst.H_PRICE + ", o." + ProductProfileConst.H_ID, 0, 0);
		return list;
	}
}
