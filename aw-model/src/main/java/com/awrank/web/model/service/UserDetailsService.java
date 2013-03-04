package com.awrank.web.model.service;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * User: a_polyakov
 */
public interface UserDetailsService {

	public UserDetails retrieveUser(String username, String passwordHash, String userIpAddress, String sessionId);

	public AWRankingUserDetails fillUserDetails(EntryPoint entryPoint);
}
