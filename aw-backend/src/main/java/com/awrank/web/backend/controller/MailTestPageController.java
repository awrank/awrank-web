package com.awrank.web.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;

@Controller
public class MailTestPageController {
	
	@Value("${mail.smtp.server.host}")
	private String smpt_host_name;

	@Value("${mail.smtp.server.port}")
	private String smpt_port;

	@Value("${mail.smtp.username}")
	private String smpt_user_name;

	@Value("${mail.smtp.password}")
	private String smpt_password;
	
	@Value("${mail.from.email}")
	private String smpt_from_email;
	
	@Autowired
	ShareToServiceEmailImpl emailService;
	
	@RequestMapping("/mailtest")
	public ModelAndView showPlayersGrid(HttpServletRequest request,
										HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("mailtest");
		Map modelMap = new HashMap();

		modelMap.put("smpt_host_name", smpt_host_name);
		modelMap.put("smpt_port", smpt_port);
		modelMap.put("smpt_user_name", smpt_user_name);
		modelMap.put("smpt_password", smpt_password);

		mav.addAllObjects(modelMap);
		return mav;
	}
	
	@RequestMapping("/sendemail")
	public 
	@ResponseBody
	String sendTestEmail() {
	
		Properties properties = new Properties();
	    properties.put("mail.transport.protocol", "smtp");
	    properties.put("mail.smtp.host", smpt_host_name);
	    properties.put("mail.smtp.port", smpt_port);
	    properties.put("mail.smtp.auth", "true");
	      
		Session smtpSession = (Session) emailService.getAuthenticatedSession(properties, smpt_user_name, smpt_password);
		
		MimeMessage message = new MimeMessage(smtpSession);
		
		Multipart multipart = new MimeMultipart("alternative");
		BodyPart part1 = new MimeBodyPart();
		try {
			part1.setText("Hello, Your Contoso order has shipped. Thank you, John");
			BodyPart part2 = new MimeBodyPart();
			part2.setContent(
			    "<p>Hello,</p><p>Your Contoso order has <b>shipped</b>.</p><p>Thank you,<br>John</br></p>",
			    "text/html");
			multipart.addBodyPart(part1);
			multipart.addBodyPart(part2);
			message.setFrom(new InternetAddress("smpt_from_email"));
			message.addRecipient(Message.RecipientType.TO,
			   new InternetAddress("okorokhina@gmail.com"));
			message.setSubject("Your recent order");
			message.setContent(multipart);
			
			return "was sent ok";
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return e.getMessage();
		}
		

	}
	
}
