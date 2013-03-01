package com.awrank.web.backend.init.user;

import com.awrank.web.model.dao.EntryHistoryDao;
import com.awrank.web.model.dao.EntryPointDao;
import com.awrank.web.model.dao.UserDao;
import com.awrank.web.model.dao.UserRoleDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.utils.user.CurrentUserUtils;
import com.awrank.web.model.utils.user.PasswordUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: a_polyakov
 */
@Service
public class InitUser {

	@Autowired
	private UserDao userDao;
	@Autowired
	private EntryPointDao entryPointDao;
	@Autowired
	private UserRoleDao userRoleDao;

	@Autowired
	private EntryHistoryDao entryHistoryDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public User initAnonymous() {
		User user = userDao.findByEmail("anonymous@awrank.com");
		if (user == null) {
			user = new User();
			user.setApiKey("ANONYMOUS");
			user.setRefUser(null);
			user.setEmail("anonymous@awrank.com");
			user.setSkype(null);
			user.setFirstName("anonymous");
			user.setLastName("anonymous");
			user.setBirthday(null);
			user.setSecretQuestionDicCode(null);
			user.setSecretAnswer(null);
			user.setLanguage(Language.RU);
			user.setAuthorizationFailsCount(0);
			user.setAuthorizationFailsLastDate(null);
			user.setBanStartedDate(null);
			user.setCreatedDate(new DateTime());
			user.setCreatedBy(user);
			user.setLastModifiedDate(user.getCreatedDate());
			user.setLastModifiedBy(user);
			userDao.save(user);
		}
		return user;
	}

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
			user.setCreatedDate(new DateTime());
			user.setCreatedBy(user);
			user.setLastModifiedDate(user.getCreatedDate());
			user.setLastModifiedBy(user);
			userDao.save(user);

			EntryPoint entryPoint = new EntryPoint();
			entryPoint.setUser(user);
			entryPoint.setType(EntryPointType.EMAIL);
			entryPoint.setUid(user.getEmail());
			entryPoint.setPassword(PasswordUtils.hashPassword("user"));
			entryPoint.setVerifiedDate(new LocalDateTime(0));
			entryPoint.setCreatedDate(new DateTime());
			entryPoint.setLastModifiedDate(entryPoint.getCreatedDate());
			entryPointDao.save(entryPoint);

			UserRole role = new UserRole();
			role.setUser(user);
			role.setRole(Role.ROLE_USER_VERIFIED);
			role.setCreatedDate(new DateTime());
			role.setLastModifiedDate(entryPoint.getCreatedDate());
			userRoleDao.save(role);

			LocalDateTime time = LocalDateTime.now();
			LocalDateTime time2 = time.plusMillis(4000000);

			EntryHistory entryHistory = new EntryHistory();
			entryHistory.setUser(user);
			entryHistory.setEntryPoint(entryPoint);
			entryHistory.setIpAddress("0:0:0:0:0:0:0:1%0");
			entryHistory.setSuccess(true);
			entryHistory.setSigninDate(time);
			entryHistory.setSignoutDate(time2);
			entryHistory.setSessionId("init session ruser 1");
			entryHistory.setCreatedDate(new DateTime());
			entryHistory.setLastModifiedDate(entryPoint.getCreatedDate());
			entryHistoryDao.save(entryHistory);

			EntryHistory entryHistory2 = new EntryHistory();
			entryHistory2.setUser(user);
			entryHistory2.setEntryPoint(entryPoint);
			entryHistory2.setSuccess(true);
			entryHistory2.setSigninDate(time);
			entryHistory2.setSessionId("init session ruser 2");
			entryHistory2.setIpAddress("127.0.0.1");
			entryHistory2.setCreatedDate(new DateTime());
			entryHistory2.setLastModifiedDate(entryPoint.getCreatedDate());
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
			user.setCreatedDate(new DateTime());
			user.setCreatedBy(user);
			user.setLastModifiedDate(user.getCreatedDate());
			user.setLastModifiedBy(user);
			userDao.save(user);

			CurrentUserUtils.setCurrentUser(user);

			EntryPoint entryPoint = new EntryPoint();
			entryPoint.setUser(user);
			entryPoint.setType(EntryPointType.LOGIN);
			entryPoint.setUid("admin");
			entryPoint.setPassword(PasswordUtils.hashPassword("1"));
			entryPoint.setVerifiedDate(new LocalDateTime(0));
			entryPoint.setCreatedDate(new DateTime());
			entryPoint.setLastModifiedDate(entryPoint.getCreatedDate());
			entryPointDao.save(entryPoint);

			EntryPoint entryPoint2 = new EntryPoint();
			entryPoint2.setUser(user);
			entryPoint2.setType(EntryPointType.EMAIL);
			entryPoint2.setUid(user.getEmail());
			entryPoint2.setPassword(PasswordUtils.hashPassword("1"));
			entryPoint2.setVerifiedDate(new LocalDateTime(0));
			entryPoint2.setCreatedDate(new DateTime());
			entryPoint2.setLastModifiedDate(entryPoint.getCreatedDate());
			entryPointDao.save(entryPoint2);

			EntryPoint entryPoint3 = new EntryPoint();
			entryPoint3.setUser(user);
			entryPoint3.setType(EntryPointType.GOOGLE);
			entryPoint3.setUid("113359939181883937834");
			entryPoint3.setPassword(null);
			entryPoint3.setVerifiedDate(new LocalDateTime(0));
			entryPoint3.setCreatedDate(new DateTime());
			entryPoint3.setLastModifiedDate(entryPoint.getCreatedDate());
			entryPointDao.save(entryPoint3);

			UserRole role = new UserRole();
			role.setUser(user);
			role.setRole(Role.ROLE_ADMIN);
			role.setCreatedDate(new DateTime());
			role.setLastModifiedDate(entryPoint.getCreatedDate());
			userRoleDao.save(role);

			LocalDateTime time = LocalDateTime.now();
			LocalDateTime time2 = time.plusMillis(4000000);

			EntryHistory entryHistory = new EntryHistory();
			entryHistory.setUser(user);
			entryHistory.setSessionId("init session admin 1");
			entryHistory.setIpAddress("0:0:0:0:0:0:0:1%0");
			entryHistory.setSuccess(true);
			entryHistory.setSigninDate(time);
			entryHistory.setSignoutDate(time2);
			entryHistory.setEntryPoint(entryPoint);
			entryHistory.setCreatedDate(new DateTime());
			entryHistory.setLastModifiedDate(entryPoint.getCreatedDate());
			entryHistoryDao.save(entryHistory);

			EntryHistory entryHistory2 = new EntryHistory();
			entryHistory2.setUser(user);
			entryHistory2.setEntryPoint(entryPoint);
			entryHistory2.setIpAddress("127.0.0.1");
			entryHistory2.setSigninDate(time);
			entryHistory2.setSessionId("init session admin 2");
			entryHistory2.setSuccess(true);
			entryHistory2.setCreatedDate(new DateTime());
			entryHistory2.setLastModifiedDate(entryPoint.getCreatedDate());
			entryHistoryDao.save(entryHistory2);
		}
	}


}
