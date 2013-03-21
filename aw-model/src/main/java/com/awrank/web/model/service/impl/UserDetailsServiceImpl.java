package com.awrank.web.model.service.impl;

import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.service.*;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import com.awrank.web.model.utils.externalService.WIPmania;
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

	@Autowired
	private UserLimitService userLimitService;

	public UserDetailsServiceImpl() {
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public UserDetails retrieveUser(String username, String passwordHash, String userIpAddress, String sessionId, String browseName) {
		AWRankingUserDetails detail = null;
		EntryPoint entryPoint = entryPointService.findOneByUid(username);
		if (entryPoint != null) {
			User user = entryPoint.getUser();
			EntryHistory entryHistory = new EntryHistory();
			entryHistory.setUser(user);
			entryHistory.setEntryPoint(entryPoint);
			entryHistory.setIpAddress(userIpAddress);
			entryHistory.setCountryCode(WIPmania.getCountryCodeByIpAddress(entryHistory.getIpAddress()));
			if (browseName.length() > 64) browseName.substring(0, 63);
			entryHistory.setBrowseName(browseName);
			entryHistory.setSessionId(sessionId);
			entryHistory.setSigninDate(LocalDateTime.now());

			if (entryPoint.getType() == EntryPointType.GOOGLE
					|| entryPoint.getType() == EntryPointType.FACEBOOK
					|| entryPoint.getPassword().equals(passwordHash)) {
				entryHistory.setSuccess(true);
				entryHistoryService.save(entryHistory);

				// user limit
				userLimitService.createDayLimit(user.getId());

				detail = new AWRankingUserDetails(entryHistory);
				user.setAuthorizationFailsCount(0);
			} else {
				// error password
				entryHistory.setSuccess(false);
				entryHistoryService.save(entryHistory);
				user.setAuthorizationFailsCount(user.getAuthorizationFailsCount() + 1);
			}
			userService.save(user);
		}
		return detail;
	}


}
