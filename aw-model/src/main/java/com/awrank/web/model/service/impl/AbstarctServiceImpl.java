package com.awrank.web.model.service.impl;

import com.awrank.web.model.service.AbstarctService;
import org.apache.log4j.Logger;

/**
 * User: a_polyakov
 */
public class AbstarctServiceImpl implements AbstarctService {
	@Override
	public Logger getLogger() {
		return Logger.getLogger(this.getClass());
	}
}
