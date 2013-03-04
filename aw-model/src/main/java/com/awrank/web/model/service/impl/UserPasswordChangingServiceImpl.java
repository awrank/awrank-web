package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.StateChangeTokenDao;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.StateChangeToken;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.enums.StateChangeTokenType;
import com.awrank.web.model.exception.passwordchanging.PasswordChangeWasNotVerifiedException;
import com.awrank.web.model.exception.passwordchanging.PasswordChangingEmailNotSetException;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserPasswordChangingService;
import com.awrank.web.model.service.UserRoleService;
import com.awrank.web.model.service.email.EmailSenderSendGridImpl;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
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
	@Qualifier("userRoleServiceImpl")
	private UserRoleService userRoleService;


	public void send(Map params) throws PasswordChangingEmailNotSetException {
		try {
			sendGridEmailSender.send(password_xsmtp_header_category, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Boolean verify(String key, HttpServletRequest request) throws PasswordChangeWasNotVerifiedException {

		//------------ first we have to find the user email and activate it, if ok we need to find corresponding entry point and activate it
		StateChangeToken stateChangeToken = stateChangeTokenDao.select(key, StateChangeTokenType.USER_PASSWORD_CHANGE);

		if (stateChangeToken == null) return false;

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

			if (thePoint == null) return false;//not found proper entry point - can't change


			//------------ we have found point and activation record ----------

			stateChangeToken.setTokenUsedAtDate(today);
			stateChangeTokenDao.save(stateChangeToken);

			thePoint.setEndedDate(today);//entry point is no longer active
			entryPointService.save(thePoint);

			//TODO: here add record in Diary about password changing

			return true;
		}

		return false;
	}

	public void save(StateChangeToken act) {

		LocalDateTime creationDate = LocalDateTime.now();
		LocalDateTime endedDate = creationDate.plusMillis(mail_passwordchangingcode_lifetime_duration);
		act.setEndedDate(endedDate);
		stateChangeTokenDao.save(act);
	}

	public StateChangeToken findByCode(String code) {

		return stateChangeTokenDao.select(code, StateChangeTokenType.USER_PASSWORD_CHANGE);
	}

}
