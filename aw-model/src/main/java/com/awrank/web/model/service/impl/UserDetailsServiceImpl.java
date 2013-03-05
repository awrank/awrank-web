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
//	@Qualifier("userServiceImpl")
//	@Resource(name="userServiceImpl")
	private UserService userService;

	@Autowired
//	@Qualifier("entryPointServiceImpl")
//	@Resource(name="entryPointServiceImpl")
	private EntryPointService entryPointService;

	@Autowired
//	@Qualifier("entryHistoryServiceImpl")
//	@Resource(name="entryHistoryServiceImpl")
	private EntryHistoryService entryHistoryService;

	public UserDetailsServiceImpl() {
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public UserDetails retrieveUser(String username, String passwordHash, String userIpAddress, String sessionId) {
		EntryPoint entryPoint;
		if (username.indexOf('@') > 0) {
			entryPoint = entryPointService.findOneByEntryPointTypeAndUid(EntryPointType.EMAIL, username);
		} else {
			entryPoint = entryPointService.findOneByEntryPointTypeAndUid(EntryPointType.LOGIN, username);
		}

		AWRankingUserDetails detail = null;
		if (entryPoint != null) {
			User user = entryPoint.getUser();
			EntryHistory entryHistory = new EntryHistory();
			entryHistory.setUser(user);
			entryHistory.setEntryPoint(entryPoint);
			entryHistory.setIpAddress(userIpAddress);
			entryHistory.setSessionId(sessionId);
			entryHistory.setSigninDate(LocalDateTime.now());
			//TODO auto date
//			entryHistory.setCreatedDate(DateTime.now());
//			entryHistory.setLastModifiedDate(entryHistory.getCreatedDate());

			if (entryPoint.getPassword().equals(passwordHash)) {
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
