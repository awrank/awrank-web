package com.awrank.web.model.service.email;

import com.awrank.web.model.utils.emailauthentication.SMTPAPIHeader;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.LinkedList;
import java.util.Map;
import java.util.Properties;

/**
 * Implementation of {@link EmailSender} interface based on <a href="http://sendgrid.com/">SendGrid</a> service.
 *
 * @author Olga Korokhina
 */
@Service("sendGridEmailSender")
public class EmailSenderSendGridImpl implements EmailSender {

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

    //------------ other settings -----------------

    @Value("#{emailProps[mail_from_email]}")
    //@Value("${mail.from.email}")
    private String smpt_from_email;

    @Value("#{emailProps[mail_testactivation_email]}")
    //@Value("${mail.testactivation.email}")
    private String testactivation_email;

    @Value("#{emailProps[mail_testactivation_password]}")
    //@Value("${mail.testactivation.password}")
    private String testactivation_password;

    @Value("#{emailProps[mail_testactivation_verifyurl]}")
    //@Value("${mail.testactivation.verifyurl}")
    private String testactivation_url;


    /* (non-Javadoc)
      * @see com.awrank.web.model.service.email.EmailSender#send(java.lang.String, java.util.Map)
      */
    @Override
    public void send(String template, Map<String, Object> params) throws Exception {

        //------------------ configuring ------------------
        //TODO: refactor this search - build mapping and go via it
        if (params.containsKey("sgsmpt_host_name")) sgsmpt_host_name = (String) params.get("sgsmpt_host_name");
        if (params.containsKey("sgsmpt_port")) sgsmpt_port = (String) params.get("sgsmpt_port");
        if (params.containsKey("sgsmpt_user_name")) sgsmpt_user_name = (String) params.get("sgsmpt_user_name");
        if (params.containsKey("sgsmpt_password")) sgsmpt_password = (String) params.get("sgsmpt_password");
        if (params.containsKey("testactivation_email"))
            testactivation_email = (String) params.get("testactivation_email");
        if (params.containsKey("testactivation_password"))
            testactivation_password = (String) params.get("testactivation_password");
        if (params.containsKey("xsmtp_header_var_name"))
            xsmtp_header_var_name = (String) params.get("xsmtp_header_var_name");
        if (params.containsKey("xsmtp_header_var_value"))
            xsmtp_header_var_value = (String) params.get("xsmtp_header_var_value");
        if (params.containsKey("smpt_from_email")) smpt_from_email = (String) params.get("smpt_from_email");

        String localAddr = "";
        if (params.containsKey("localAddr")) localAddr = (String) params.get("localAddr");

        String remoteAddr = "";
        if (params.containsKey("remoteAddr")) localAddr = (String) params.get("remoteAddr");

        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", sgsmpt_host_name);
        properties.put("mail.smtp.port", sgsmpt_port);
        properties.put("mail.smtp.auth", "true");

        Session smtpSession = (Session) getAuthenticatedSession(properties, sgsmpt_user_name, sgsmpt_password);

        smtpSession.setDebug(true);//for debug purposes, set to false or delete lately

        SMTPAPIHeader header = new SMTPAPIHeader();

        LinkedList<String> recipients = new LinkedList<String>();
        recipients.add(testactivation_email);
        header.addTo(recipients);

        String key = SMTPAuthenticator.getHashed256(testactivation_email + "." + testactivation_password + "." + localAddr + "." + remoteAddr);
        if ((xsmtp_header_var_value != null) && String.valueOf(xsmtp_header_var_value).length() > 0)
            key = xsmtp_header_var_value;

        System.out.println("local" + localAddr);
        System.out.println("remote: " + remoteAddr);

        LinkedList<String> activation_keys = new LinkedList<String>();
        activation_keys.add(key);

        if (xsmtp_header_var_name == null || String.valueOf(xsmtp_header_var_name).length() == 0)
            header.addSubVal("%activation_key%", activation_keys);
        else header.addSubVal(xsmtp_header_var_name, activation_keys);

        if (template == null) header.setCategory("email activation");
        else header.setCategory(template);

        //----------------- sending ---------------------------

        MimeMessage message = new MimeMessage(smtpSession);

        Multipart multipart = new MimeMultipart("alternative");
        BodyPart part1 = new MimeBodyPart();


        part1.setText("Thank you for registration at AWranking. Please click on the activation link below");
        BodyPart part2 = new MimeBodyPart();

        /**
         * The idea with hash building is to use user's email, password and IP to build the key. This key is stored in db. Once on verification we find
         * such a key exists we get the user associated with this key. We fetch his email and password from db and take from request his current IP. On these values
         * we build a second key and if these 2 keys are equal - we verify the email. Otherwise not.
         */

        if (key == null)
            key = SMTPAuthenticator.getHashed256(testactivation_email + "." + testactivation_password + "." + localAddr + "." + remoteAddr);
        StringBuilder bldr = new StringBuilder("<a href=");
        bldr.append("\"");
        bldr.append(testactivation_url);
        bldr.append(key);
        bldr.append("\"");
        bldr.append(">");
        bldr.append(testactivation_url);
        bldr.append(key);
        bldr.append("</a>");
        String mess = bldr.toString();
        part2.setContent(mess, "text/html");
        multipart.addBodyPart(part1);
        multipart.addBodyPart(part2);
        message.setFrom(new InternetAddress(smpt_from_email));
        message.addRecipient(Message.RecipientType.TO,
                new InternetAddress(testactivation_email));
        message.setSubject("Your need to verify email");
        message.setContent(multipart);

        if (header != null) {
            System.out.println(header.asJSON());
            message.addHeader("X-SMTPAPI", header.asJSON());
        }

        share(smtpSession, message);

    }


    protected void share(Object session, Object message) throws Exception {

        Transport transport = ((Session) session).getTransport();
        try {
            // Connect the transport object.
            transport.connect();
            // Send the message.
            transport.sendMessage((MimeMessage) message, ((MimeMessage) message).getRecipients(Message.RecipientType.TO));
        } catch (javax.mail.AuthenticationFailedException e) {

            System.out.println(e.getMessage());
        } finally {
            // Close the connection.
            transport.close();
        }
    }


    protected Object getAuthenticatedSession(Properties properties, String username, String password) {

        Authenticator auth = new SMTPAuthenticator(username, password);
        Session mailSession = Session.getDefaultInstance(properties, auth); // bad solution - session was shared,
        //first selected SMPT service settings were applied to all further attempts - even with different credentials
        //Session mailSession = Session.getInstance(properties, auth);
        return mailSession;
    }

}
