package com.awrank.web.model.service.impl;

import com.awrank.web.model.service.AbstractService;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Base abstract implementation for services.
 *
 * @author Alex Polyakov
 * @author Andrew Stoyaltsev
 */
public abstract class AbstractServiceImpl implements AbstractService {

	@Override
	public Logger getLogger() {
		return Logger.getLogger(this.getClass());
	}

	/**
	 * Minimal base map for creation json response. You can add to this map
	 * your specific fields.
	 * @return {@link HashMap} instance with at least 'result' field.
	 */
	public Map<String, String> getPositiveResponse() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "ok");
		return result;
	}

}
