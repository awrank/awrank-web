package com.awrank.web.model.service.impl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.service.UserService;
import org.joda.time.DateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.awrank.web.model.dao.EntryPointDao;
import com.awrank.web.model.dao.UserDao;
import com.awrank.web.model.dao.UserEmailActivationDao;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.UserEmailActivation;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.email.EmailSenderSendGridImpl;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;

/**
 * @author Olga Korokhina
 *
 */
@Service
//@PropertySource("/WEB-INF/properties/application.properties")
public class UserServiceImpl implements UserService {

	//----------------  Send Grip SMTP ------------
	
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
	private UserDao userDao;
	
	@Autowired
	private EntryPointDao entryPointDao;
	
	@Autowired
	private UserEmailActivationDao userEmailActivationDao;
	
	@Autowired
	EmailSenderSendGridImpl sendGridEmailSender;
	
	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#add(com.awrank.web.model.domain.User)
	 */
	@Override
	//@Transactional
	public void add(User user) throws UserNotCreatedException{
		
		try{ //can be thrown javax.persistence.PersistenceException etc.
			
			userDao.persist(user);
		
		}catch(Exception e){
			
			throw new UserNotCreatedException();
			
		}

	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#delete(com.awrank.web.model.domain.User)
	 */
	@Override
	@Transactional
	public void delete(User user) throws UserNotDeletedException{
		
		userDao.remove(user);

	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#save(com.awrank.web.model.domain.User)
	 */
	@Override
	public void save(User user) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#findById(java.lang.Integer)
	 */
	@Override
	public List<User> findById(Long id) {
		// TODO Auto-generated method stub
		return new ArrayList<User>();
	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.UserService#findByEmail(java.lang.String)
	 */
	@Override
	public List<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return new ArrayList<User>();
	}

	@Override
	public List<User> findByAPIKey(String key) {
		// TODO Auto-generated method stub
		return new ArrayList<User>();
	}
	
	@Transactional
	@Override
	public void register(UserRegistrationFormPojo form) throws UserNotCreatedException{
	
		DateTime creationDate = DateTime.now();
		
		//--------------------- create user ---------------------------
		
		User user = new User();
		
		user.setApiKey(form.getApiKey());
		user.setFirstName(form.getFirstName());
		user.setLastName(form.getLastName());
		user.setEmail(form.getEmail());
		//user.setLanguage(form.getLanguage());
		
		add(user);
		
		//---------------- create entrance point for him -----------------
		
		EntryPoint entryPoint = new EntryPoint();
		
		entryPoint.setUser(user);
		
		entryPoint.setUid(user.getEmail());
		entryPoint.setPassword(form.getPassword());
		entryPoint.setType(EntryPointType.EMAIL.EMAIL);//on registration we demand User to have email
		
		entryPointDao.save(entryPoint);
		
		
		//---------------- sending verification email --------------------
		
		String key;
		try {
			
			key = SMTPAuthenticator.getHashed256(form.getEmail()+"."+form.getPassword()+"."+form.getUserLocalAddr() +"."+form.getUserLocalAddr());
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			throw new UserNotCreatedException();
		}
		
		Map<String, Object> params = new HashMap<String, Object>();
		
		params.put("localAddr", form.getUserLocalAddr());
		params.put("remoteAddr", form.getUserRemoteAddr());
		params.put("testactivation_email", user.getEmail());
		params.put("testactivation_password", form.getPassword());
		
		try {
			
			sendGridEmailSender.send(xsmtp_header_category, params);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//-------------- store to db information about verification email was sent -------------------------------------------
		
		UserEmailActivation userEmailActivation = new UserEmailActivation();
	
		userEmailActivation.setCode(key);
		userEmailActivation.setUser(user);
		userEmailActivation.setEmail(user.getEmail());
		
		Date today = new Date(creationDate.getMillis());
		Date endedDate = new Date(mail_verificationcode_lifetime_duration + creationDate.getMillis());
		
		userEmailActivation.setCreatedDate(today);
		userEmailActivation.setEndedDate(endedDate);
		
		userEmailActivation.setIpAddress(form.getUserRemoteAddr());//Check later if we need remote or local IP here
		
		userEmailActivationDao.persist(userEmailActivation);
		
	}

	@Override
	public UserEmailActivation findEmailVerificationByCode(String code) {
		
		return userEmailActivationDao.select(code);
		
	}	
	
	@Override
	public EntryPoint findEntryPointForUser(String code) {
		
		return null; //entryPointDao.select(code);
		
	}	
}