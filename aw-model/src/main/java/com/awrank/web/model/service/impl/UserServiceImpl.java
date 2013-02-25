package com.awrank.web.model.service.impl;


import com.awrank.web.model.dao.UserDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserEmailActivationService;
import com.awrank.web.model.service.UserRoleService;
import com.awrank.web.model.service.UserService;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;

	@Autowired
	private UserEmailActivationService userEmailActivationService;

	@Autowired
	@Qualifier("userRoleServiceImpl")
	private UserRoleService userRoleService;

	@Secured("IS_AUTHENTICATED_ANONYMOUSLY")//<global-method-security jsr250-annotations="enabled" /> in config
	//@PreAuthorize("isAnonymous()")// <global-method-security pre-post-annotations="enabled" /> in config
	@Transactional
	@Override
	public User register(UserRegistrationFormPojo form, HttpServletRequest request) throws UserNotCreatedException, EntryPointNotCreatedException, UserActivationEmailNotSetException {

		//--------------------- create user ---------------------------

		User user = new User();

		user.setApiKey(form.getApiKey());
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setEmail(form.getEmail());
		//user.setLanguage(form.getLanguage());
		user.setAuthorizationFailsCount(0);

		add(user);

		//---------------- create entrance point for him -----------------

		EntryPoint entryPoint = new EntryPoint();

		entryPoint.setUser(user);

		entryPoint.setUid(user.getEmail());
		entryPoint.setPassword(form.getPassword());
		entryPoint.setType(EntryPointType.EMAIL.EMAIL);//on registration we demand User to have email

		entryPointService.add(entryPoint);

		//---------------- sending verification email --------------------

		String key;
		try {

			key = SMTPAuthenticator.getHashed256(form.getEmail() + "." + form.getPassword() + "." + form.getUserLocalAddr() + "." + form.getUserRemoteAddr());

		} catch (Exception e1) {

			e1.printStackTrace();
			throw new UserNotCreatedException();
		}

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("localAddr", form.getUserLocalAddr());
		params.put("remoteAddr", form.getUserRemoteAddr());
		params.put("testactivation_email", user.getEmail());
		params.put("testactivation_password", form.getPassword());

		userEmailActivationService.send(params);

//-------------- store to db information about verification email was sent -------------------------------------------

		UserEmailActivation userEmailActivation = new UserEmailActivation();

		userEmailActivation.setCode(key);
		userEmailActivation.setUser(user);
		userEmailActivation.setEmail(user.getEmail());
		userEmailActivation.setIpAddress(form.getUserRemoteAddr());//Check later if we need remote or local IP here

		userEmailActivationService.save(userEmailActivation);

//-------------- here we need save a role for user -------------	

		UserRole role = new UserRole();
		role.setUser(user);
		role.setRole(Role.ROLE_USER);

		userRoleService.save(role);

		return user;
	}


	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#add(com.awrank.web.model.domain.User)
	 */
	@Override
	//@Transactional
	public void add(User user) throws UserNotCreatedException {

		try { //can be thrown javax.persistence.PersistenceException etc.

			//Session session = sessionFactory.getCurrentSession();
			//session.save(user);
			userDao.save(user);

			//session.flush();

		} catch (Exception e) {

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
	public List<User> getAll(){
		
		return (List<User>) userDao.findAll();
		
	}
	
	@Override
	public Page<User> getPage(Pageable pageable){
		
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