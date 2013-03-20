package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.StateChangeTokenDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.enums.StateChangeTokenType;
import com.awrank.web.model.exception.AwRankException;
import com.awrank.web.model.exception.passwordchanging.PasswordChangeWasNotVerifiedException;
import com.awrank.web.model.exception.passwordchanging.PasswordChangingEmailNotSetException;
import com.awrank.web.model.service.*;
import com.awrank.web.model.service.email.EmailSenderSendGridImpl;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;
import com.awrank.web.model.utils.externalService.WIPmania;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserPasswordChangingServiceImpl extends UserPasswordChangingService {

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

	@Value("#{emailProps[password_xsmtp_header_category]}")
	//@Value("${mail.from.email}")
	private String password_xsmtp_header_category;

//------------ other email settings -----------------

	@Value("#{emailProps[mail_from_email]}")
	//@Value("${mail.from.email}")
	private String smpt_from_email;

	@Value("#{emailProps[mail_password_changeurl]}")
	//@Value("${mail.testactivation.verifyurl}")
	private String mail_password_changeurl;

//--------------------------------------------------

	@Value("#{appProps[mail_passwordchangingcode_lifetime_duration]}")
	private Integer mail_passwordchangingcode_lifetime_duration;

	@Autowired
	private StateChangeTokenDao stateChangeTokenDao;

	@Autowired
	EmailSenderSendGridImpl sendGridEmailSender;

	@Autowired
	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;

	@Autowired
	@Qualifier("entryHistoryServiceImpl")
	private EntryHistoryService entryHistoryService;

	@Autowired
	@Qualifier("userRoleServiceImpl")
	private UserRoleService userRoleService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Autowired
	//@Qualifier("diaryServiceImpl")
	private DiaryService diaryService;

	public void send(Map params) throws PasswordChangingEmailNotSetException {
		try {
			sendGridEmailSender.send(password_xsmtp_header_category, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public EntryPoint verify(String key, HttpServletRequest request) throws PasswordChangeWasNotVerifiedException {

		//------------ first we have to find the user email and activate it, if ok we need to find corresponding entry point and activate it
		StateChangeToken stateChangeToken = stateChangeTokenDao.select(key, StateChangeTokenType.USER_PASSWORD_CHANGE);

		if (stateChangeToken == null) return null;

		//-------------- we found and we activate -----------

		LocalDateTime today = LocalDateTime.now();
		LocalDateTime ended = stateChangeToken.getEndedDate();
		if (ended.isAfter(today)) { //found and fresh

			//---------------  find entry point ----------------

			User user = stateChangeToken.getUser();
			EntryPoint thePoint = entryPointService.findOneByEntryPointTypeAndUid(EntryPointType.EMAIL, user.getEmail());
			String builtKey;
			
			if (thePoint == null) return null;//not found proper entry point - can't change
			
			//------------ we have found point and activation record, building ethalon key to compare ----------

			try {
				builtKey = SMTPAuthenticator.getHashed256(user.getEmail() + "." + stateChangeToken.getNewValue() + "." + request.getRemoteAddr());
			} catch (Exception e) {
			
				e.printStackTrace();
				throw new PasswordChangeWasNotVerifiedException();
			}
			
			if(builtKey.compareTo(key) != 0) return null;
			
			//-------------- everything is ok, changing ---------------
			
			user.setAuthorizationFailsCount(0);
			userService.save(user);
			
			stateChangeToken.setTokenUsedAtDate(today);
			stateChangeTokenDao.save(stateChangeToken);

			thePoint.setEndedDate(today);//entry point is no longer active
			entryPointService.save(thePoint);
			
			EntryPoint newPoint =  new EntryPoint();
			newPoint.setPassword(stateChangeToken.getNewValue());
			newPoint.setUser(user);
			newPoint.setType(thePoint.getType());
			newPoint.setVerifiedDate(today);
			newPoint.setUid(user.getEmail());
			entryPointService.save(newPoint);
			
			//---------- find/create EntryHistory ------------

			List<EntryHistory> entryHistoryList = entryHistoryService.findBySessionId(request.getSession().getId());
			EntryHistory entryHistory;

			if (entryHistoryList.size() == 0) {//create one if not found

				entryHistory = new EntryHistory();
				entryHistory.setUser(user);
				entryHistory.setSessionId(request.getSession().getId());
				entryHistory.setIpAddress(request.getRemoteAddr());
				entryHistory.setCountryCode(WIPmania.getCountryCodeByIpAddress(entryHistory.getIpAddress()));
				entryHistory.setEntryPoint(newPoint);
				entryHistory.setSigninDate(today);
				String brHeader = request.getHeader("user-agent");
				if(brHeader.length() > 64) brHeader.substring(0, 63);
				entryHistory.setSuccess(true);
				entryHistory.setBrowseName(brHeader);
				entryHistoryService.save(entryHistory);
			} else entryHistory = entryHistoryList.get(0);
			//------ here add record in Diary about password changing

			Diary drec = new Diary();
			drec.setEvent(DiaryEvent.CHANGE_PASSWORD);
			drec.setUser(user);
			drec.setCreatedBy(user);
			drec.setEntryHistory(entryHistory);
			drec.setOldValue(stateChangeToken.getValue());
			drec.setNewValue(stateChangeToken.getNewValue());
			diaryService.save(drec);

			return newPoint;
		}

		return null;
	}

	/**
	 * Save new token if no of same type with same token found or update existing
	 * @throws AwRankException 
	 */
	public void save(StateChangeToken stateChangeToken) throws AwRankException{
		
		LocalDateTime creationDate = LocalDateTime.now();
		LocalDateTime endedDate = creationDate.plusMillis(mail_passwordchangingcode_lifetime_duration);
		
		StateChangeToken found = findByCode(stateChangeToken.getToken());
		if(found == null){
			
			stateChangeToken.setEndedDate(endedDate);
			stateChangeTokenDao.save(stateChangeToken);
		
		}else if(found.getUser() == stateChangeToken.getUser()){//update for same user, not used  && found.getTokenUsedAtDate() == null
			
			found.setEndedDate(endedDate);
			found.setTokenUsedAtDate(null);
			stateChangeTokenDao.save(found);
		}
		else throw new AwRankException("Attempt to save StateChangeToken with existing code for different user in password change");

	}

	public StateChangeToken findByCode(String code) {

		return stateChangeTokenDao.select(code, StateChangeTokenType.USER_PASSWORD_CHANGE);
	}

	//------------------- refactor out it not needed ---------------

	public void setDiaryService(DiaryServiceImpl value) {
		diaryService = value;
	}

	public DiaryService getDiaryService() {
		return diaryService;
	}
}
