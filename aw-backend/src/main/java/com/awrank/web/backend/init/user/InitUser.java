package com.awrank.web.backend.init.user;

import com.awrank.web.model.dao.EntryPointDao;
import com.awrank.web.model.dao.UserDao;
import com.awrank.web.model.dao.UserRoleDao;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.Language;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.UserRole;
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
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void initAnonymous() {
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
			
			UserRole role = new UserRole();
			role.setUser(user);
			role.setRole(Role.ROLE_USER);
			userRoleDao.save(role);
		}
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
			
			UserRole role = new UserRole();
			role.setUser(user);
			role.setRole(Role.ROLE_USER_VERIFIED);
			userRoleDao.save(role);
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
			
			UserRole role = new UserRole();
			role.setUser(user);
			role.setRole(Role.ROLE_ADMIN);
			userRoleDao.save(role);
		}
	}


}
