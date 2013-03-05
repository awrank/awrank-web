package com.awrank.web.model.service.impl;

import com.awrank.web.model.service.AbstractService;
import org.apache.log4j.Logger;

/**
 * User: a_polyakov
 */
public class AbstractServiceImpl implements AbstractService {
	@Override
	public Logger getLogger() {
		return Logger.getLogger(this.getClass());
	}
}
