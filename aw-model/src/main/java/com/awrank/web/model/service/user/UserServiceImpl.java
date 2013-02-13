package com.awrank.web.model.service.user;

import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.awrank.web.model.domain.EntryPointType;
import org.joda.time.DateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.awrank.web.model.dao.EntryPointDao;
import com.awrank.web.model.dao.user.UserDao;
import com.awrank.web.model.dao.user.UserDaoImpl;
import com.awrank.web.model.dao.useremailactivation.UserEmailActivationDao;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.UserEmailActivation;
import com.awrank.web.model.domain.constant.EAuthenticationMethod;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.sharing.ShareToServiceEmailImpl;
import com.awrank.web.model.service.user.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.utils.emailauthentication.SMTPAPIHeader;
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
	
	@Value("#{emailProps[mail_verificationcode_lifetime_duration]}")
	private Integer mail_verificationcode_lifetime_duration;
	
//--------------------------------------------------
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EntryPointDao entryPointDao;
	
	@Autowired
	private UserEmailActivationDao userEmailActivationDao;
	
	@Autowired
	ShareToServiceEmailImpl emailService;
			
	
	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.user.UserService#add(com.awrank.web.model.domain.User)
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
	 * @see com.awrank.web.model.service.user.UserService#delete(com.awrank.web.model.domain.User)
	 */
	@Override
	@Transactional
	public void delete(User user) throws UserNotDeletedException{
		
		userDao.remove(user);

	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.user.UserService#save(com.awrank.web.model.domain.User)
	 */
	@Override
	public void save(User user) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.user.UserService#findById(java.lang.Integer)
	 */
	@Override
	public List<User> findById(Long id) {
		// TODO Auto-generated method stub
		return new ArrayList<User>();
	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.user.UserService#findByEmail(java.lang.String)
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
		user.setLanguage(form.getLanguage());
		
		add(user);
		
		//---------------- create entrance point for him -----------------
		
		EntryPoint entryPoint = new EntryPoint();
		
		entryPoint.setUser(user);
		
		entryPoint.setUid(user.getEmail());
		entryPoint.setPassword(form.getPassword());
		entryPoint.setType(EntryPointType.EMAIL.EMAIL);//on registration we demand User to have email
		
		entryPointDao.save(entryPoint);
		
		
		//---------------- sending verification email --------------------
		
		Properties properties = new Properties();
	    properties.put("mail.transport.protocol", "smtp");
	    properties.put("mail.smtp.host", sgsmpt_host_name);
	    properties.put("mail.smtp.port", sgsmpt_port);
	    properties.put("mail.smtp.auth", "true");
	      
		Session smtpSession = (Session) emailService.getAuthenticatedSession(properties, sgsmpt_user_name, sgsmpt_password);
		smtpSession.setDebug(true);//for debug purposes, set to false or delete lately
		
		SMTPAPIHeader header=  new SMTPAPIHeader();
		
		LinkedList<String> recipients = new LinkedList<String>();
		recipients.add(form.getEmail());
		header.addTo(recipients);
		
		String key;
		try {
			
			key = SMTPAuthenticator.getHashed256(form.getEmail()+"."+form.getPassword()+"."+form.getUserLocalAddr() +"."+form.getUserLocalAddr());
			
		} catch (Exception e1) {
			
			e1.printStackTrace();
			throw new UserNotCreatedException();
		}
		
		key = xsmtp_header_var_value;
		
		LinkedList<String> activation_keys = new LinkedList<String>();
		activation_keys.add(key);
		
		if(xsmtp_header_var_name == null || String.valueOf(xsmtp_header_var_name).length() == 0 ) header.addSubVal("%activation_key%",activation_keys);
		else header.addSubVal(xsmtp_header_var_name,activation_keys);
		
		if(xsmtp_header_category == null) header.setCategory("email activation");
		else header.setCategory(xsmtp_header_category);
	
		MimeMessage message = new MimeMessage(smtpSession);
		
		Multipart multipart = new MimeMultipart("alternative");
		BodyPart part1 = new MimeBodyPart();
		try {
			//TODO: here replace with String from dictionary
			part1.setText("Thank you for registration at AWranking. Please click on the activation link below");
			BodyPart part2 = new MimeBodyPart();
			
			StringBuilder bldr = new StringBuilder("<a href=");
			bldr.append( "\"");
			bldr.append(testactivation_url);
			bldr.append(key);
			bldr.append( "\"");
			bldr.append(">");
			bldr.append(testactivation_url);
			bldr.append(key);
			bldr.append("</a>");
			String mess =  bldr.toString();
			part2.setContent(mess, "text/html");
			multipart.addBodyPart(part1);
			multipart.addBodyPart(part2);
			message.setFrom(new InternetAddress(smpt_from_email));
			message.addRecipient(Message.RecipientType.TO,
			   new InternetAddress(form.getEmail()));
			message.setSubject("Your need to verify email");
			message.setContent(multipart);
			
			if(header != null){
				System.out.println(header.asJSON());	
				message.addHeader("X-SMTPAPI", header.asJSON());
			}
			
			emailService.share(smtpSession, message);
			
		} catch (Exception e) {
			
			e.printStackTrace();
			throw new UserNotCreatedException();
		}
		finally{
			
			properties.clear();
			//smtpSession.flush();
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
}