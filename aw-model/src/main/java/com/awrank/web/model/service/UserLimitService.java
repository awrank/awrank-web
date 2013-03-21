package com.awrank.web.model.service;

import com.awrank.web.model.domain.UserLimit;

/**
 * @author Alex Polyakov
 */
public interface UserLimitService extends AbstractService {
	public void save(UserLimit userLimit);

	public void createDayLimit(Long userId);
}
