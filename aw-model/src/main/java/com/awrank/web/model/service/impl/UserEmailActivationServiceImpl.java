package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.UserEmailActivationDao;
import com.awrank.web.model.domain.*;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.emailactivation.UserActivationWasNotVerifiedException;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserEmailActivationService;
import com.awrank.web.model.service.UserRoleService;
import com.awrank.web.model.service.email.EmailSenderSendGridImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import org.joda.time.LocalDateTime;

@Service
public class UserEmailActivationServiceImpl implements UserEmailActivationService {

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
    private UserEmailActivationDao userEmailActivationDao;

    @Autowired
    EmailSenderSendGridImpl sendGridEmailSender;

    @Autowired
    @Qualifier("entryPointServiceImpl")
    private EntryPointService entryPointService;

    @Autowired
    @Qualifier("userRoleServiceImpl")
    private UserRoleService userRoleService;

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
    public Boolean verify(String key, HttpServletRequest request) throws UserActivationWasNotVerifiedException {

        //------------ first we have to find the user email and activate it, if ok we need to find corresponding entry point and activate it
        UserEmailActivation userEmailActivation = userEmailActivationDao.select(key);

        if (userEmailActivation == null) return false;

        //-------------- we found and we activate -----------
        
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime ended = userEmailActivation.getEndedDate();
        if (ended.isAfter(today)) { //found and fresh

            //---------------  find entry point ----------------

            User user = userEmailActivation.getUser();
            List<EntryPoint> points = entryPointService.findEntryPointForUser(user);
            EntryPoint thePoint = null;

            for (EntryPoint point : points) {

                if (point.getType() == EntryPointType.EMAIL) thePoint = point;
            }

            if (thePoint == null) return false;//not found proper entry point - can't activate


            //------------ we have found point and activation record ----------

            userEmailActivation.setEmailVerifiedDate(today);
            userEmailActivationDao.save(userEmailActivation);

            thePoint.setVerifiedDate(today);//TODO: refactor DAOs or/and db: for email verification we store verification date as Date, for entry point - as DateTime. Possible bottleneck here - we probably will need to check they are the sane!!!
            entryPointService.save(thePoint);

            //---------- we add record concerning user role to user_roles ----

            UserRole role = new UserRole();
            role.setUser(user);
            role.setRole(Role.ROLE_USER_VERIFIED);
            userRoleService.save(role);
            return true;
        }
       
        return false;
    }

    @Override
    public void save(UserEmailActivation userEmailActivation) {
    	LocalDateTime creationDate = LocalDateTime.now();
    	LocalDateTime endedDate = creationDate.plusMillis(mail_verificationcode_lifetime_duration);
        userEmailActivation.setEndedDate(endedDate);
        userEmailActivationDao.save(userEmailActivation);
    }

    @Override
    public UserEmailActivation findEmailVerificationByCode(String code) {
        // TODO Auto-generated method stub
        return null;
    }
}
