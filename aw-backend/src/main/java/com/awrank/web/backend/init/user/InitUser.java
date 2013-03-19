package com.awrank.web.backend.init.user;

import com.awrank.web.model.dao.EntryHistoryDao;
import com.awrank.web.model.dao.EntryPointDao;
import com.awrank.web.model.dao.UserDao;
import com.awrank.web.model.dao.UserRoleDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.utils.user.AuditorAwareImpl;
import com.awrank.web.model.utils.user.PasswordUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Alex Polyakov
 */
@Service
public class InitUser {

	@Autowired
	AuditorAwareImpl auditorAware;

	@Autowired
	private UserDao userDao;
	@Autowired
	private EntryPointDao entryPointDao;
	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private EntryHistoryDao entryHistoryDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void initRegularUser() {
		User user = userDao.findByEmail("user@awrank.com");
		if (user == null) {
			user = new User();
			user.setApiKey("REGULAR_USER");
			user.setRefUser(null);
			user.setEmail("user@awrank.com");
			user.setSkype(null);
			user.setFirstName("user");
			user.setLastName("user");
			user.setBirthday(null);
			user.setSecretQuestionDicCode(null);
			user.setSecretAnswer(null);
			user.setLanguage(Language.EN);
			user.setAuthorizationFailsCount(0);
			user.setAuthorizationFailsLastDate(null);
			user.setBanStartedDate(null);
			userDao.save(user);

			UserRole role = new UserRole(user, Role.ROLE_USER_VERIFIED);
			userRoleDao.save(role);

			EntryPoint entryPoint = new EntryPoint();
			user.getEntryPoints().add(entryPoint);
			entryPoint.setUser(user);
			entryPoint.setType(EntryPointType.EMAIL);
			entryPoint.setUid(user.getEmail());
			entryPoint.setPassword(PasswordUtils.hashPassword("user"));
			entryPoint.setVerifiedDate(new LocalDateTime(0));
			entryPointDao.save(entryPoint);

			LocalDateTime time = LocalDateTime.now();
			LocalDateTime time2 = time.plusMillis(4000000);

			EntryHistory entryHistory = new EntryHistory();
			entryHistory.setUser(user);
			entryHistory.setEntryPoint(entryPoint);
			entryHistory.setIpAddress("0:0:0:0:0:0:0:1%0");
			entryHistory.setCountryCode("UA");
			entryHistory.setBrowseName("undefined");
			entryHistory.setSuccess(true);
			entryHistory.setSigninDate(time);
			entryHistory.setSignoutDate(time2);
			entryHistory.setSessionId("init session ruser 1");
			entryHistoryDao.save(entryHistory);

			EntryHistory entryHistory2 = new EntryHistory();
			entryHistory2.setUser(user);
			entryHistory2.setEntryPoint(entryPoint);
			entryHistory2.setSuccess(true);
			entryHistory2.setSigninDate(time);
			entryHistory2.setSessionId("init session ruser 2");
			entryHistory2.setIpAddress("127.0.0.1");
			entryHistory2.setCountryCode("UA");
			entryHistory2.setBrowseName("undefined");
			entryHistoryDao.save(entryHistory2);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void initAdmin() {
		User user = userDao.findByEmail("admin@awrank.com");
		if (user == null) {
			user = new User();
			user.setApiKey("ADMIN");
			user.setRefUser(null);
			user.setEmail("admin@awrank.com");
			user.setSkype(null);
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setBirthday(null);
			user.setSecretQuestionDicCode(null);
			user.setSecretAnswer(null);
			user.setLanguage(Language.RU);
			user.setAuthorizationFailsCount(0);
			user.setAuthorizationFailsLastDate(null);
			user.setBanStartedDate(null);
			user.setCreatedBy(user);
			user.setLastModifiedBy(user);
			userDao.save(user);

			UserRole role = new UserRole(user, Role.ROLE_ADMIN);
			role.setCreatedBy(user);
			role.setLastModifiedBy(user);
			userRoleDao.save(role);

			EntryPoint entryPoint = new EntryPoint();
			user.getEntryPoints().add(entryPoint);
			entryPoint.setUser(user);
			entryPoint.setType(EntryPointType.LOGIN);
			entryPoint.setUid("admin");
			entryPoint.setPassword(PasswordUtils.hashPassword("1"));
			entryPoint.setVerifiedDate(new LocalDateTime(0));
			entryPoint.setCreatedBy(user);
			entryPoint.setLastModifiedBy(user);
			entryPointDao.save(entryPoint);

			auditorAware.setCurrentAuditor(entryPoint);

			EntryPoint entryPoint2 = new EntryPoint();
			user.getEntryPoints().add(entryPoint2);
			entryPoint2.setUser(user);
			entryPoint2.setType(EntryPointType.EMAIL);
			entryPoint2.setUid(user.getEmail());
			entryPoint2.setPassword(PasswordUtils.hashPassword("1"));
			entryPoint2.setVerifiedDate(new LocalDateTime(0));
			entryPointDao.save(entryPoint2);

			EntryPoint entryPoint3 = new EntryPoint();
			user.getEntryPoints().add(entryPoint3);
			entryPoint3.setUser(user);
			entryPoint3.setType(EntryPointType.GOOGLE);
			entryPoint3.setUid("113359939181883937834");
			entryPoint3.setPassword(null);
			entryPoint3.setVerifiedDate(new LocalDateTime(0));
			entryPointDao.save(entryPoint3);

			LocalDateTime time = LocalDateTime.now();
			LocalDateTime time2 = time.plusMillis(4000000);

			EntryHistory entryHistory = new EntryHistory();
			entryHistory.setUser(user);
			entryHistory.setSessionId("init session admin 1");
			entryHistory.setIpAddress("0:0:0:0:0:0:0:1%0");
			entryHistory.setCountryCode("UA");
			entryHistory.setBrowseName("undefined");
			entryHistory.setSuccess(true);
			entryHistory.setSigninDate(time);
			entryHistory.setSignoutDate(time2);
			entryHistory.setEntryPoint(entryPoint);
			entryHistoryDao.save(entryHistory);

			EntryHistory entryHistory2 = new EntryHistory();
			entryHistory2.setUser(user);
			entryHistory2.setEntryPoint(entryPoint);
			entryHistory2.setIpAddress("127.0.0.1");
			entryHistory2.setCountryCode("UA");
			entryHistory2.setBrowseName("undefined");
			entryHistory2.setSigninDate(time);
			entryHistory2.setSessionId("init session admin 2");
			entryHistory2.setSuccess(true);
			entryHistoryDao.save(entryHistory2);
		}
	}


}
