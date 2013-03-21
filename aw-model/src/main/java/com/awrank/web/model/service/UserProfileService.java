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
import com.awrank.web.model.service.impl.pojos.UserNewPasswordFormPojo;
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
	
	/**
	 * User for "beautiful" interface - no need in "request" here, we are ion same page with AJAX calls
	 * @param form
	 * @param principal
	 * @throws UserActivationEmailNotSetException
	 */
	@SuppressWarnings("rawtypes")
	public Map sendNewEmailVerificationLinkOnEmailManualChange(UserRegistrationFormPojo form, Principal principal) throws UserActivationEmailNotSetException;
	
	@SuppressWarnings("rawtypes")
	public Map sendPasswordChangingLinkToEmail(UserRegistrationFormPojo form, HttpServletRequest request, Principal principal) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Map sendPasswordForgorLinkToEmail(String email, HttpServletRequest request, Principal principal) throws Exception;
	
	@SuppressWarnings("rawtypes")
	public Map sendPasswordChangingLinkToEmail(UserNewPasswordFormPojo form,Principal principal);

	@SuppressWarnings("rawtypes")
	public  Map updateProfileData(UserProfileDataFormPojo form, Principal principal);
	
}
