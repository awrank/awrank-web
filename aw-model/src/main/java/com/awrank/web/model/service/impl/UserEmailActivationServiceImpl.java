package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.StateChangeTokenDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.enums.StateChangeTokenType;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.emailactivation.UserActivationWasNotVerifiedException;
import com.awrank.web.model.service.DiaryService;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserEmailActivationService;
import com.awrank.web.model.service.UserRoleService;
import com.awrank.web.model.service.email.EmailSenderSendGridImpl;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

@Service
public class UserEmailActivationServiceImpl extends UserEmailActivationService {

	//----------------  Send Grid SMTP ------------

	@Value("#{emailProps[mail_sg_smtp_server_host]}")
	//@Value("${mail.sg.smtp.server.host}")
	private String sgsmpt_host_name;

	@Value("#{emailProps[mail_sg_smtp_server_port]}")
	//@Value("${mail.sg.smtp.server.port}")
	private String sgsmpt_port;

	@Value("#{emailProps[mail_sg_smtp_username]}")
	//@Value("${mail.sg.smtp.username}")
	private String sgsmpt_user_name;

	@Value("#{emailProps[mail_sg_smtp_password]}")
	//@Value("${mail.sg.smtp.password}")
	private String sgsmpt_password;

	@Value("#{emailProps[mail_xsmtp_header_category]}")
	//@Value("${mail.xsmtp.header.category}")
	private String xsmtp_header_category;

	@Value("#{emailProps[mail_xsmtp_header_var_name]}")
	//@Value("${mail.xsmtp.header.var.name}")
	private String xsmtp_header_var_name;

	@Value("#{emailProps[mail_xsmtp_header_var_value]}")
	//@Value("${mail.xsmtp.header.var.value}")
	private String xsmtp_header_var_value;

//------------ other email settings -----------------

	@Value("#{emailProps[mail_from_email]}")
	//@Value("${mail.from.email}")
	private String smpt_from_email;

	@Value("#{emailProps[mail_testactivation_verifyurl]}")
	//@Value("${mail.testactivation.verifyurl}")
	private String testactivation_url;

//-- email verification code lifetime duration, milliseconds --

	@Value("#{appProps[mail_verificationcode_lifetime_duration]}")
	private Integer mail_verificationcode_lifetime_duration;

//--------------------------------------------------

	@Autowired
	private StateChangeTokenDao stateChangeTokenDao;

	@Autowired
	EmailSenderSendGridImpl sendGridEmailSender;

	@Autowired
	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;

	@Autowired
	@Qualifier("userRoleServiceImpl")
	private UserRoleService userRoleService;

	@Autowired
	@Qualifier("diaryServiceImpl")
	private DiaryService diaryService;
	
	@Autowired
	@Qualifier("entryHistoryServiceImpl")
	private EntryHistoryService entryHistoryService;
	
	@Override
	public void send(Map params) throws UserActivationEmailNotSetException {
		try {
			sendGridEmailSender.send(xsmtp_header_category, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	@Transactional
	public EntryPoint verify(String key, HttpServletRequest request) throws UserActivationWasNotVerifiedException {

		//------------ first we have to find the user email and activate it, if ok we need to find corresponding entry point and activate it
		StateChangeToken stateChangeToken = stateChangeTokenDao.select(key, StateChangeTokenType.USER_EMAIL_VERIFICATION);
		
		if(stateChangeToken != null){//first activation
		
			//-------------- we found and we activate -----------
	
			LocalDateTime today = LocalDateTime.now();
			LocalDateTime ended = stateChangeToken.getEndedDate();
			if (ended.isAfter(today)) { //found and fresh
	
				//---------------  find entry point ----------------
	
				User user = stateChangeToken.getUser();
				Set<EntryPoint> points = user.getEntryPoints();
				EntryPoint thePoint = null;
	
				for (EntryPoint point : points) {
	
					if (point.getType() == EntryPointType.EMAIL) thePoint = point;
				}
	
				if (thePoint == null) return null;//not found proper entry point - can't activate
	
				//--------- check if activation made from same IP it was requested: build second key following same rule -------
				
				String builtKey;
				System.out.println(request.getLocalAddr());
				System.out.println(request.getRemoteAddr());
				try {
					
					 builtKey = SMTPAuthenticator.getHashed256(user.getEmail() + "." + thePoint.getPassword() + "." + request.getLocalAddr() + "." + request.getRemoteAddr());
					
				} catch (Exception e) {
					
					throw new UserActivationWasNotVerifiedException(UserActivationWasNotVerifiedException.message_key_not_built);
					
				}
	
				if(key.compareTo(builtKey) != 0) throw new UserActivationWasNotVerifiedException();
				
				//------------ we have found point and activation record ----------
	
				stateChangeToken.setTokenUsedAtDate(today);
				stateChangeTokenDao.save(stateChangeToken);
	
				thePoint.setVerifiedDate(today);
				entryPointService.save(thePoint);
	
				//---------------------- save to Diary ----------------------------
				
				EntryHistory entryHistory = entryHistoryService.getLatestEntryForUser(user);
				
				Diary dr = new Diary();
				dr.setUser(user);
				dr.setCreatedBy(user);
				dr.setEvent(DiaryEvent.VERIFY_EMAIL);
				dr.setEntryHistory(entryHistory);//cannot be null
				dr.setNewValue(user.getEmail());
				this.diaryService.save(dr);
	
				//---------- we add record concerning user role to user_roles ----
				UserRole role = new UserRole();
				role.setUser(user);
				role.setRole(Role.ROLE_USER_VERIFIED);
				userRoleService.save(role);
				
				return thePoint;
			}
		}
		//----------- check if this is email changing ----
		 stateChangeToken = stateChangeTokenDao.select(key, StateChangeTokenType.USER_EMAIL_CHANGE);
		 if(stateChangeToken != null){//email change
				
				//-------------- we found and we activate new -----------
		
				LocalDateTime today = LocalDateTime.now();
				LocalDateTime ended = stateChangeToken.getEndedDate();
				if (ended.isAfter(today)) { //found and fresh
		
				//----------  find current entry point and set expired ----------------
	
				User user = stateChangeToken.getUser();
				Set<EntryPoint> points = user.getEntryPoints();
				EntryPoint oldPoint = entryPointService.findOneByEntryPointTypeAndUid(EntryPointType.EMAIL, stateChangeToken.getValue());
				
				if (oldPoint == null) return null;//not found proper entry point - can't activate
	
				//--------- check if activation made from same IP it was requested: build second key following same rule -------
				
				String builtKey;
				System.out.println(request.getLocalAddr());
				System.out.println(request.getRemoteAddr());
				try {
					
					 builtKey = SMTPAuthenticator.getHashed256(stateChangeToken.getNewValue() + "." + oldPoint.getPassword() + "." + request.getLocalAddr() + "." + request.getRemoteAddr());
					
				} catch (Exception e) {
					
					throw new UserActivationWasNotVerifiedException(UserActivationWasNotVerifiedException.message_key_not_built);
					
				}
	
				if(key.compareTo(builtKey) != 0) throw new UserActivationWasNotVerifiedException();
				
				
				
				oldPoint.setEndedDate(today);
				entryPointService.save(oldPoint);
				
				//-------------- create new entry point -------------
				
				EntryPoint newPoint = new EntryPoint();
				newPoint.setUser(user);
				newPoint.setUid(stateChangeToken.getNewValue());
				newPoint.setType(EntryPointType.EMAIL);
				newPoint.setPassword(oldPoint.getPassword());
				newPoint.setVerifiedDate(today);
				
				entryPointService.save(newPoint);
				
				//------------ we have found point and activation record ----------
	
				stateChangeToken.setTokenUsedAtDate(today);
				stateChangeTokenDao.save(stateChangeToken);
	
				//---------------------- save to Diary ----------------------------
				
				EntryHistory entryHistory = entryHistoryService.getLatestEntryForUser(user);
				
				Diary dr = new Diary();
				dr.setUser(user);
				dr.setCreatedBy(user);
				dr.setEntryHistory(entryHistory);
				dr.setEvent(DiaryEvent.CHANGE_EMAIL);
				dr.setOldValue(stateChangeToken.getValue());
				dr.setNewValue(stateChangeToken.getNewValue());
				this.diaryService.save(dr);
				
				return newPoint;
			}
		}

		return null;
	}

	@Override
	public void save(StateChangeToken stateChangeToken) {
		LocalDateTime creationDate = LocalDateTime.now();
		LocalDateTime endedDate = creationDate.plusMillis(mail_verificationcode_lifetime_duration);
		stateChangeToken.setEndedDate(endedDate);
		stateChangeTokenDao.save(stateChangeToken);
	}

	@Override
	public StateChangeToken findByCode(String code) {
		return stateChangeTokenDao.select(code, StateChangeTokenType.USER_EMAIL_VERIFICATION);
	}
}
