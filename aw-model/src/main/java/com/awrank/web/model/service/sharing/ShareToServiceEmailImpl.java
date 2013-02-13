package com.awrank.web.model.service.sharing;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;

/**
 * @author Olga Korokhina
 * 
 * Service for sending emails
 *
 */
@Service("emailService")
public class ShareToServiceEmailImpl implements ShareToService {
	

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.sharing.ShareToService#share()
	 */
	@Override
	public void share(Object session, Object message) throws Exception {
	
		Transport transport = ((Session) session).getTransport();
		try{
			// Connect the transport object.
			transport.connect();
			// Send the message.
			transport.sendMessage((MimeMessage) message, ((MimeMessage) message).getRecipients(Message.RecipientType.TO));
		}catch(javax.mail.AuthenticationFailedException e){
			
			System.out.println(e.getMessage());
		}
		finally{
			// Close the connection.
			transport.close();
		}
	}

	@Override
	public Object getAuthenticatedSession(Properties properties, String username, String password) {
		
		Authenticator auth = new SMTPAuthenticator(username, password);
		Session mailSession = Session.getDefaultInstance(properties, auth); // bad solution - session was shared, 
		//first selected SMPT service settings were applied to all further attempts - even with different credentials
		//Session mailSession = Session.getInstance(properties, auth);
		return mailSession;
	}

}
