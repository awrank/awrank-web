package com.awrank.web.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.BodyPart;
import javax.mail.Message;
//import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import com.awrank.web.model.service.sharing.ShareToServiceEmailImpl;
import com.awrank.web.model.utils.emailauthentication.SMTPAPIHeader;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

@Controller
public class MailTestPageController {
	
//----------------  Jango SMTP ------------
	
	@Value("${mail.j.smtp.server.host}")
	private String jsmpt_host_name;

	@Value("${mail.j.smtp.server.port}")
	private String jsmpt_port;

	@Value("${mail.j.smtp.username}")
	private String jsmpt_user_name;

	@Value("${mail.j.smtp.password}")
	private String jsmpt_password;
	
//----------------  Send Grip SMTP ------------
	
	
	@Value("${mail.sg.smtp.server.host}")
	private String sgsmpt_host_name;

	@Value("${mail.sg.smtp.server.port}")
	private String sgsmpt_port;

	@Value("${mail.sg.smtp.username}")
	private String sgsmpt_user_name;

	@Value("${mail.sg.smtp.password}")
	private String sgsmpt_password;
	
//------------ other settings -----------------
	
	@Value("${mail.from.email}")
	private String smpt_from_email;
	
	@Value("${mail.testactivation.email}")
	private String testactivation_email;
	
	@Value("${mail.testactivation.password}")
	private String testactivation_password;
	
	@Value("${mail.testactivation.verifyurl}")
	private String testactivation_url;
	
	@Autowired
	ShareToServiceEmailImpl emailService;
	
	@RequestMapping("/mailtest")
	public ModelAndView showPlayersGrid(HttpServletRequest request,
										HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("mailtest");
		Map modelMap = new HashMap();

		modelMap.put("jsmpt_host_name", jsmpt_host_name);
		modelMap.put("jsmpt_port", jsmpt_port);
		modelMap.put("jsmpt_user_name", jsmpt_user_name);
		modelMap.put("jsmpt_password", jsmpt_password);
		
		modelMap.put("sgsmpt_host_name", sgsmpt_host_name);
		modelMap.put("sgsmpt_port", sgsmpt_port);
		modelMap.put("sgsmpt_user_name", sgsmpt_user_name);
		modelMap.put("sgsmpt_password", sgsmpt_password);
		
		modelMap.put("smpt_from_email", smpt_from_email);
		modelMap.put("testactivation_email", testactivation_email);
		modelMap.put("testactivation_password", testactivation_password);
		
		mav.addAllObjects(modelMap);
		return mav;
	}
	
	
	@RequestMapping(method=RequestMethod.GET, value="/verifyemail/{key}")
	public 
	@ResponseBody
	String verifyTestEmail(@PathVariable("key") String key, HttpServletRequest request) throws Exception {
	
		//Here we check if such a key exists in db, if yes - fetch user's email and password and build the key with same hasher but with current user IP.
		
		String new_key = SMTPAuthenticator.getHashed256(testactivation_email+"."+testactivation_password+"."+request.getLocalAddr() +"."+request.getRemoteAddr());
		
		System.out.println("remote host"+request.getRemoteHost());
		System.out.println("remote adr: "+request.getRemoteAddr());
		System.out.println("X-Forwarded-For: "+request.getHeader("X-Forwarded-For"));
		 
		System.out.println("new_key: "+new_key);
	
		if( new_key.compareToIgnoreCase(key) == 0) return "key " +key+ " verified ok";
		else return "key " +key+ " not verified";
	}
	
	@RequestMapping("/sendtestjungosmtp")
	public 
	@ResponseBody
	String sendTestEmailJSMPT(HttpServletRequest request) {
	
		Properties properties = new Properties();
	    properties.put("mail.transport.protocol", "smtp");
	    properties.put("mail.smtp.host", jsmpt_host_name);
	    properties.put("mail.smtp.port", jsmpt_port);
	    properties.put("mail.smtp.auth", "true");
	      
		Session smtpSession = (Session) emailService.getAuthenticatedSession(properties, jsmpt_user_name, jsmpt_password);
		
		smtpSession.setDebug(true);//for debug purposes, set to false or delete lately
		
		return sendTestSMPTMailToSession(smtpSession, request, properties, null);
	}
	
	
	@RequestMapping("/sendtestsendgrid")
	public 
	@ResponseBody
	String sendTestEmailSG(HttpServletRequest request) throws Exception {
	
		Properties properties = new Properties();
	    properties.put("mail.transport.protocol", "smtp");
	    properties.put("mail.smtp.host", sgsmpt_host_name);
	    properties.put("mail.smtp.port", sgsmpt_port);
	    properties.put("mail.smtp.auth", "true");
	      
		Session smtpSession = (Session) emailService.getAuthenticatedSession(properties, sgsmpt_user_name, sgsmpt_password);
		
		smtpSession.setDebug(true);//for debug purposes, set to false or delete lately
		
		SMTPAPIHeader header=  new SMTPAPIHeader();
		
		LinkedList<String> recipients = new LinkedList<String>();
		recipients.add("okorokhina@gmail.com");
		header.addTo(recipients);
		
		String key = SMTPAuthenticator.getHashed256(testactivation_email+"."+testactivation_password+"."+request.getLocalAddr() +"."+request.getRemoteAddr());
		
		System.out.println("local"+request.getLocalAddr());
		System.out.println("remote: "+request.getRemoteAddr());
		
		LinkedList<String> activation_keys = new LinkedList<String>();
		activation_keys.add(key);
		header.addSubVal("%activation_key%",activation_keys);
		
		header.setCategory("email activation");
		
		return sendTestSMPTMailToSession(smtpSession, request, properties, header);
	}
	
	private String sendTestSMPTMailToSession(Session smtpSession, HttpServletRequest request, Properties properties, SMTPAPIHeader header) {
		
		MimeMessage message = new MimeMessage(smtpSession);
		
		Multipart multipart = new MimeMultipart("alternative");
		BodyPart part1 = new MimeBodyPart();
		try {
			part1.setText("Thank you for registration at AWranking. Please click on the activation link below");
			BodyPart part2 = new MimeBodyPart();
			
			
			/**
			 * The idea with hash building is to use user's email, password and IP to build the key. This key is stored in db. Once on verification we find
			 * such a key exists we get the user associated with this key. We fetch his email and password from db and take from request his current IP. On these values 
			 * we build a second key and if these 2 keys are equal - we verify the email. Otehrwise not.
			 */
			
			String key = SMTPAuthenticator.getHashed256(testactivation_email+"."+testactivation_password+"."+request.getLocalAddr() +"."+request.getRemoteAddr());//Base64(Hash256(testactivation_email+"."+testactivation_password));
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
			   new InternetAddress("okorokhina@gmail.com"));
			message.setSubject("Your need to verify email");
			message.setContent(multipart);
			
			if(header != null){
				System.out.println(header.asJSON());	
				message.addHeader("X-SMTPAPI", header.asJSON());
			}
			emailService.share(smtpSession, message);
			
			return "was sent ok";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return "an error during sending, "+e.toString();
		}
		finally{
			
			//properties.clear();
		}

	}
	
}
