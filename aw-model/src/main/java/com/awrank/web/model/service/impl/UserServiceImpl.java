package com.awrank.web.model.service.impl;


import com.awrank.web.model.dao.UserDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.enums.StateChangeTokenType;
import com.awrank.web.model.exception.AwRankException;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.StateChangeTokenService;
import com.awrank.web.model.service.UserRoleService;
import com.awrank.web.model.service.UserService;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link UserService} interface.
 *
 * @author Olga Korokhina
 */
@Service
//@PropertySource("/WEB-INF/properties/application.properties")
public class UserServiceImpl extends AbstarctServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
//	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;

	//	@Autowired
//	@Qualifier("userEmailActivationServiceImpl")
	@Resource(name = "userEmailActivationServiceImpl")
	private StateChangeTokenService userEmailActivationService;

	@Autowired
//	@Qualifier("userRoleServiceImpl")
	private UserRoleService userRoleService;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public EntryPoint register(UserRegistrationFormPojo form, HttpServletRequest request) throws UserNotCreatedException, EntryPointNotCreatedException, UserActivationEmailNotSetException {

		//--------------------- create user ---------------------------

		User user = new User();
		user.setApiKey(form.getApiKey());
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setEmail(form.getEmail());
		if (form.getLanguage() != null) {
			user.setLanguage(form.getLanguage());
		} else {
			user.setLanguage(Language.EN);
		}
		user.setAuthorizationFailsCount(0);
		add(user);

		//---------------- create entrance point for him -----------------

		EntryPoint entryPoint = new EntryPoint();
		entryPoint.setUser(user);
		entryPoint.setUid(user.getEmail());
		entryPoint.setPassword(form.getPassword());//here password shall be already hashed
		entryPoint.setType(EntryPointType.EMAIL);//on registration we demand User to have email
		entryPointService.add(entryPoint);

		//---------------- sending verification email --------------------

		String key;
		try {
			key = SMTPAuthenticator.getHashed256(form.getEmail() + "." + form.getPassword() + "." + form.getUserLocalAddress() + "." + form.getUserRemoteAddress());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new UserNotCreatedException();
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("localAddr", form.getUserLocalAddress());
		params.put("remoteAddr", form.getUserRemoteAddress());
		params.put("testactivation_email", user.getEmail());
		params.put("testactivation_password", form.getPassword());

		try {
			userEmailActivationService.send(params);
		} catch (AwRankException e) {
			// TODO Auto-generated catch block
			getLogger().error(e.getMessage(), e);
		}

//-------------- store to db information about verification email was sent -------------------------------------------

		StateChangeToken stateChangeToken = new StateChangeToken();
		stateChangeToken.setToken(key);
		stateChangeToken.setType(StateChangeTokenType.USER_EMAIL_VERIFICATION);
		stateChangeToken.setUser(user);
		stateChangeToken.setValue(user.getEmail());
		stateChangeToken.setIpAddress(form.getUserRemoteAddress());//Check later if we need remote or local IP here
		userEmailActivationService.save(stateChangeToken);

//-------------- here we need save a role for user -------------

		UserRole role = new UserRole();
		role.setUser(user);
		role.setRole(Role.ROLE_USER);
		userRoleService.save(role);

		return entryPoint;
	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#add(com.awrank.web.model.domain.User)
	 */
	@Override
	public void add(User user) throws UserNotCreatedException {
		try {
			userDao.save(user);
		} catch (Exception e) {
			getLogger().error(e.getMessage(), e);
			throw new UserNotCreatedException();
		}
	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#delete(com.awrank.web.model.domain.User)
	 */
	@Override
	@Transactional
	public void delete(User user) throws UserNotDeletedException {
		userDao.delete(user);
	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#save(com.awrank.web.model.domain.User)
	 */
	@Override
	public void save(User user) {
		userDao.save(user);
	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#findById(java.lang.Integer)
	 */
	@Override
	public User findOne(Long id) {
		return userDao.findOne(id);
	}

	@Override
	public List<User> getAll() {
		return (List<User>) userDao.findAll();
	}

	@Override
	public Page<User> getPage(Pageable pageable) {
		return (Page<User>) userDao.findAll(pageable);
	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#findByEmail(java.lang.String)
	 */
	@Override
	public User findOneByEmail(String email) {
		return userDao.findByEmail(email);
	}

	@Override
	public User findByAPIKey(String key) {
		return userDao.findByIPIKey(key);
	}

}