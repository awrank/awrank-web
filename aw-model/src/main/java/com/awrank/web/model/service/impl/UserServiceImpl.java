package com.awrank.web.model.service.impl;


import com.awrank.web.model.dao.UserDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.enums.StateChangeTokenType;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointByEmailNotFoundException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.entrypoint.EntryPointWrongCurrentPasswordException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.*;
import com.awrank.web.model.service.email.EmailSenderSendGridImpl;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;
import com.awrank.web.model.utils.user.PasswordUtils;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Implementation of {@link UserService} interface.
 *
 * @author Olga Korokhina
 */
@Service
//@PropertySource("/WEB-INF/properties/application.properties")
public class UserServiceImpl extends AbstractServiceImpl implements UserService {

	@Value("#{emailProps[blocked_xsmtp_header_category]}")
	private String blocked_xsmtp_header_category;

	@Value("#{emailProps[unblocked_xsmtp_header_category]}")
	private String unblocked_xsmtp_header_category;
	
	@Autowired
	private UserDao userDao;

	@Autowired
	@Qualifier("entryHistoryServiceImpl")
	private EntryHistoryService entryHistoryService;

	@Autowired
	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;

	@Autowired
	@Qualifier("diaryServiceImpl")
	private DiaryService diaryService;

	//	@Autowired
//	@Qualifier("userEmailActivationServiceImpl")
	@Resource(name = "userEmailActivationServiceImpl")
	private StateChangeTokenService userEmailActivationService;

	@Autowired
//	@Qualifier("userRoleServiceImpl")
	private UserRoleService userRoleService;

	@Autowired
	EmailSenderSendGridImpl sendGridEmailSender;

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
		entryPoint.setPassword(PasswordUtils.hashPassword(form.getPassword()));//here password shall be already hashed
		entryPoint.setType(EntryPointType.EMAIL);//on registration we demand User to have email
		entryPointService.add(entryPoint);

		//---------------- sending verification email --------------------
		if (!sendingVerificationEmail(user, form.getEmail(), entryPoint.getPassword(), form.getUserLocalAddress(), form.getUserRemoteAddress()))
			throw new UserNotCreatedException();

		//-------------- here we need save a role for user -------------
		UserRole role = new UserRole(user, Role.ROLE_USER);
		userRoleService.save(role);

		return entryPoint;
	}

	private boolean sendingVerificationEmail(User user, String email, String password, String localAddress, String remoteAddress) {
		boolean success = false;
		try {
			String key = SMTPAuthenticator.getHashed256(email + "." + password + "." + localAddress + "." + remoteAddress);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("localAddr", localAddress);
			params.put("remoteAddr", remoteAddress);
			params.put("testactivation_email", email);
			params.put("testactivation_password", password);

			userEmailActivationService.send(params);

//-------------- store to db information about verification email was sent -------------------------------------------

			StateChangeToken stateChangeToken = new StateChangeToken();
			stateChangeToken.setToken(key);
			stateChangeToken.setType(StateChangeTokenType.USER_EMAIL_VERIFICATION);
			stateChangeToken.setUser(user);
			stateChangeToken.setValue(email);
			stateChangeToken.setIpAddress(remoteAddress);//Check later if we need remote or local IP here
			userEmailActivationService.save(stateChangeToken);
			success = true;
		} catch (Exception e) {
			getLogger().error(e.getMessage(), e);
		}
		return success;
	}


	public User blockUser(User user, Principal principal) {

		user.setBanStartedDate(LocalDateTime.now());
		save(user);

		//Shall we introduce the "enabled" to EntryPoint and set it here to false?
		//EntryPoint entryPoint = entryPointService.findOneByEntryPointTypeAndUid(EntryPointType.EMAIL, user.getEmail());

		AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

		//------ here add record in Diary about user was blocked

		Diary drec = new Diary();
		drec.setEvent(DiaryEvent.BLOCKED);
		drec.setUser(user);

		//---------- find/create EntryHistory ------------

		List<EntryHistory> entryHistoryList = entryHistoryService.findBySessionId(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getSessionId());
		EntryHistory entryHistory;

		if (entryHistoryList.size() == 0) {//create one if not found

			entryHistory = new EntryHistory();
			entryHistory.setUser(user);//associated with Admin's entry history record

			entryHistory.setSessionId(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getSessionId());
			entryHistory.setIpAddress(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getRemoteAddress());

			EntryPoint entryPoint = entryPointService.findOneByUid(details.getUid());
			entryHistory.setEntryPoint(entryPoint);
			entryHistory.setSigninDate(LocalDateTime.now());
			entryHistoryService.save(entryHistory);
		} else entryHistory = entryHistoryList.get(0);

		drec.setEntryHistory(entryHistory);
		diaryService.save(drec);

		//----------send email to user about he was blocked -------------

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("testactivation_email", user.getEmail());
		try {
			sendGridEmailSender.send(blocked_xsmtp_header_category, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}

	public User unblockUser(User user, Principal principal) {
		
		user.setBanStartedDate(null);
		save(user);

		//Shall we introduce the "enabled" to EntryPoint and set it here to false?
		//EntryPoint entryPoint = entryPointService.findOneByEntryPointTypeAndUid(EntryPointType.EMAIL, user.getEmail());

		AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

		//------ here add record in Diary about user was blocked

		Diary drec = new Diary();
		drec.setEvent(DiaryEvent.UNBLOCKED);
		drec.setUser(user);

		//---------- find/create EntryHistory ------------

		List<EntryHistory> entryHistoryList = entryHistoryService.findBySessionId(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getSessionId());
		EntryHistory entryHistory;

		if (entryHistoryList.size() == 0) {//create one if not found

			entryHistory = new EntryHistory();
			entryHistory.setUser(user);//associated with Admin's entry history record

			entryHistory.setSessionId(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getSessionId());
			entryHistory.setIpAddress(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getRemoteAddress());

			EntryPoint entryPoint = entryPointService.findOneByUid(details.getUid());
			entryHistory.setEntryPoint(entryPoint);
			entryHistory.setSigninDate(LocalDateTime.now());
			entryHistoryService.save(entryHistory);
		} else entryHistory = entryHistoryList.get(0);

		drec.setEntryHistory(entryHistory);
		diaryService.save(drec);

		//----------send email to user about he was blocked -------------

		Map<String, Object> params = new HashMap<String, Object>();

		params.put("testactivation_email", user.getEmail());
		try {
			sendGridEmailSender.send(unblocked_xsmtp_header_category, params);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return user;
	}
	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#add(com.awrank.web.model.domain.User)
	 */
	@Override
	public User add(User user) throws UserNotCreatedException {
		try {
			return userDao.save(user);
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
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
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

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#recoveryPasswordIntoEmail(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void recoveryPasswordIntoEmail(String email, String localAddress, String remoteAddress) throws EntryPointByEmailNotFoundException {
		EntryPoint entryPoint = entryPointService.findOneByUid(email);
		if (entryPoint == null)
			throw EntryPointByEmailNotFoundException.getInstance();
		Iterator<UserRole> iterator = entryPoint.getUser().getUserRoles().iterator();
		while (iterator.hasNext()) {
			UserRole userRole = iterator.next();
			if (userRole.getRole() == Role.ROLE_USER_VERIFIED) {
				iterator.remove();
				userRoleService.delete(userRole);
			}
		}
		String newPassword = PasswordUtils.generatePassword(8);
		String newPasswordHash = PasswordUtils.hashPassword(newPassword);
		entryPoint.setPassword(newPasswordHash);
		sendingVerificationEmail(entryPoint.getUser(), email, newPassword, localAddress, remoteAddress);
	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#changePassword(java.lang.String)
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void changePassword(String uid, String oldPassword, String newPassword) throws EntryPointByEmailNotFoundException, EntryPointWrongCurrentPasswordException {
		EntryPoint entryPoint = entryPointService.findOneByUid(uid);
		if (entryPoint == null)
			throw EntryPointByEmailNotFoundException.getInstance();
		if (!PasswordUtils.hashPassword(oldPassword).equals(entryPoint.getPassword()))
			throw EntryPointWrongCurrentPasswordException.getInstance();
		entryPoint.setPassword(PasswordUtils.hashPassword(newPassword));
		entryPointService.save(entryPoint);
		// TODO send email
	}

}