package com.awrank.web.model.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * User: a_polyakov
 */
public interface UserDetailsService extends AbstarctService {

	public UserDetails retrieveUser(String username, String passwordHash, String userIpAddress, String sessionId);
}
