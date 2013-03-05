package com.awrank.web.model.service.impl;

import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserDetailsService;
import com.awrank.web.model.service.UserService;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Olga Korokhina
 */
@Service()
public class UserDetailsServiceImpl extends AbstractServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Autowired
	private EntryPointService entryPointService;

	@Autowired
	private EntryHistoryService entryHistoryService;

	public UserDetailsServiceImpl() {
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public UserDetails retrieveUser(String username, String passwordHash, String userIpAddress, String sessionId) {
		AWRankingUserDetails detail = null;
		EntryPoint entryPoint = entryPointService.findOneByUid(username);
		if (entryPoint != null) {
			User user = entryPoint.getUser();
			EntryHistory entryHistory = new EntryHistory();
			entryHistory.setUser(user);
			entryHistory.setEntryPoint(entryPoint);
			entryHistory.setIpAddress(userIpAddress);
			entryHistory.setSessionId(sessionId);
			entryHistory.setSigninDate(LocalDateTime.now());

			if (entryPoint.getType() == EntryPointType.GOOGLE
					|| entryPoint.getType() == EntryPointType.FACEBOOK
					|| entryPoint.getPassword().equals(passwordHash)) {
				detail = new AWRankingUserDetails(entryPoint);
				entryHistory.setSuccess(true);
				user.setAuthorizationFailsCount(0);
			} else {
				// error password
				entryHistory.setSuccess(false);
				user.setAuthorizationFailsCount(user.getAuthorizationFailsCount() + 1);
			}
			entryHistoryService.save(entryHistory);
			userService.save(user);
		}
		return detail;
	}
}
