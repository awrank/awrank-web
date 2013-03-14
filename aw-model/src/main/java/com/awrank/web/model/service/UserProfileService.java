package com.awrank.web.model.service;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.passwordchanging.PasswordChangingEmailNotSetException;
import com.awrank.web.model.service.impl.pojos.UserProfileDataFormPojo;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;

/**
 * service for user profile specific operations, called from UserProfileController
 * @author Olga Korokhina
 */
public interface UserProfileService extends AbstractService {

	public Page<EntryHistory> getUserLoginHistory(Pageable pageable, Principal principal);
	
	public UserProfileDataFormPojo getUserProfileDataForPrincipal(Principal principal);
	
	public UserProfileDataFormPojo getUserProfileDataForUser(User user);
	
	public void sendNewEmailVerificationLinkOnEmailManualChange(UserRegistrationFormPojo form, HttpServletRequest request, Principal principal) throws UserActivationEmailNotSetException;
	
	@SuppressWarnings("rawtypes")
	public Map sendPasswordChangingLinkToEmail(UserRegistrationFormPojo form, HttpServletRequest request, Principal principal) throws Exception;
}
