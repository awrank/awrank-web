package com.awrank.web.model.service;

import org.apache.log4j.Logger;

/**
 * Basic interface for abstract service implementation.
 *
 * @author Alex Polyakov
 */
public interface AbstractService {

	/**
	 * Gets logger.
	 * @return {@link Logger} instance.
	 */
	Logger getLogger();

}