package com.awrank.web.model.service.impl;


import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.Language;
import com.awrank.web.model.domain.StateChangeToken;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.enums.StateChangeTokenType;
import com.awrank.web.model.exception.AwRankException;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserPasswordChangingService;
import com.awrank.web.model.exception.passwordchanging.PasswordChangingEmailNotSetException;
import com.awrank.web.model.service.StateChangeTokenService;
import com.awrank.web.model.service.impl.pojos.UserNewPasswordFormPojo;
import com.awrank.web.model.service.impl.pojos.UserProfileDataFormPojo;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import com.awrank.web.model.service.UserProfileService;
import com.awrank.web.model.service.UserService;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import com.awrank.web.model.utils.emailauthentication.SMTPAuthenticator;
import com.awrank.web.model.utils.user.PasswordUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

/**
 * @author Olga Korokhina
 */
@Service
public class UserProfileServiceImpl extends AbstractServiceImpl implements UserProfileService {

	@Autowired
	private UserService userService;

	@Autowired
	private EntryPointService entryPointService;

	@Autowired
	private EntryHistoryService entryHistoryService;
	
	@Resource(name = "userEmailActivationServiceImpl")
	private StateChangeTokenService userEmailActivationService;
	
	@Autowired
	private UserPasswordChangingService userPasswordChangingService;
	
	@Value("#{emailProps[forgot_password_xsmtp_header_category]}")
	private String forgot_password_xsmtp_header_category;
	
	public Page<EntryHistory> getUserLoginHistory(Pageable pageable, Principal principal){
		
		
		AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
		Page<EntryHistory> allEntryHistory = entryHistoryService.getPageByUserId(details.getUserId(), pageable);

		return allEntryHistory;
		
	}
	
	
	public UserProfileDataFormPojo getUserProfileDataForPrincipal(Principal principal){
		
		AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
		User user = this.userService.findOne(details.getUserId());
		
		UserProfileDataFormPojo userdata = new UserProfileDataFormPojo();
		userdata.fillWithUserData(user);
		userdata.setLanguage(user.getLanguage().name());
		return userdata;
	}
	
	public UserProfileDataFormPojo getUserProfileDataForUser(User user){
		
		UserProfileDataFormPojo userdata = new UserProfileDataFormPojo();
		userdata.fillWithUserData(user);
		userdata.setLanguage(user.getLanguage().name());
		return userdata;
	}
	
	public void sendNewEmailVerificationLinkOnEmailManualChange(UserRegistrationFormPojo form, HttpServletRequest request, Principal principal) throws UserActivationEmailNotSetException{
		
		AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

//		---------------- sending verification email on new email--------------------

		String key;
		try {
			key = SMTPAuthenticator.getHashed256(form.getEmail() + "." + details.getPassword() + "." + request.getLocalAddr() + "." + request.getRemoteAddr());
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new UserActivationEmailNotSetException();
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("localAddr", request.getLocalAddr());
		params.put("remoteAddr", request.getRemoteAddr());
		params.put("testactivation_email", form.getEmail());
		params.put("testactivation_password", details.getPassword());

		try {
			userEmailActivationService.send(params);
		} catch (AwRankException e) {

			getLogger().error(e.getMessage(), e);
			throw new UserActivationEmailNotSetException();
		}

//		-------------- saving to db -----------------------------

		StateChangeToken token = new StateChangeToken();
		token.setToken(key);
		token.setType(StateChangeTokenType.USER_EMAIL_CHANGE);
		token.setCreatedBy(new User(details.getUserId()));
		try{
			token.setIpAddress(((WebAuthenticationDetails) ((UsernamePasswordAuthenticationToken) principal).getDetails()).getRemoteAddress());//or use taken from request one here?
		}catch(Exception ex){
			
			token.setIpAddress(request.getRemoteAddr());
		}
		token.setNewValue(form.getEmail());//new email
		token.setValue(details.getUserEmail());//current email
		
		User user = this.userService.findOne(details.getUserId());
		token.setUser(user);
		
		try {
			userEmailActivationService.save(token);
			
		} catch (AwRankException e) {
			
			throw new UserActivationEmailNotSetException();
			//return this.getNegativeResponseMap(e.getLocalizedMessage());	
		}
	}
	
	
	public Map sendNewEmailVerificationLinkOnEmailManualChange(UserRegistrationFormPojo form, Principal principal) throws UserActivationEmailNotSetException{
		
		AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

//		---------------- sending verification email on new email--------------------
		String principalIP = form.getRemoteIP();
		Object currentDetails = ((UsernamePasswordAuthenticationToken) principal).getDetails();
		if (currentDetails instanceof WebAuthenticationDetails) {
			WebAuthenticationDetails webAuthenticationDetails = (WebAuthenticationDetails) currentDetails;
			principalIP = webAuthenticationDetails.getRemoteAddress();
		}

		String key;
		
		try {
			key = SMTPAuthenticator.getHashed256(form.getEmail() + "." + details.getPassword() + "." + form.getLocalIP() + "." + form.getRemoteIP());
		} catch (Exception e1) {
			getLogger().error(e1.getMessage(), e1);
			//throw new UserActivationEmailNotSetException();
			return this.getNegativeResponseMap(UserActivationEmailNotSetException.USER_EMAIL_ACTIVATION_NOT_SENT);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("localAddr", form.getLocalIP());
		params.put("remoteAddr", form.getRemoteIP());
		params.put("testactivation_email", form.getEmail());
		params.put("testactivation_password", details.getPassword());

		try {
			userEmailActivationService.send(params);
		} catch (AwRankException e) {

			getLogger().error(e.getMessage(), e);
			//throw new UserActivationEmailNotSetException();
			return this.getNegativeResponseMap(UserActivationEmailNotSetException.USER_EMAIL_ACTIVATION_NOT_SENT);
		}

//		-------------- saving to db -----------------------------

		StateChangeToken token = new StateChangeToken();
		token.setToken(key);
		token.setType(StateChangeTokenType.USER_EMAIL_CHANGE);
		token.setCreatedBy(new User(details.getUserId()));
		try{
			token.setIpAddress(principalIP);//or use taken from request one here?
		}catch(Exception ex){
			
			token.setIpAddress("");
		}
		token.setNewValue(form.getEmail());//new email
		token.setValue(details.getUserEmail());//current email
		
		User user = this.userService.findOne(details.getUserId());
		token.setUser(user);
		try {
			userEmailActivationService.save(token);
			
		} catch (AwRankException e) {
			
			return this.getNegativeResponseMap(e.getLocalizedMessage());	
		}
		
		return getPositiveResponseMap("PROFILE_EMAIL_UPDATED_SUCCESSFULLY");
	}
	
	@SuppressWarnings("rawtypes")
	public Map sendPasswordChangingLinkToEmail(UserRegistrationFormPojo form, HttpServletRequest request, Principal principal) throws Exception{
				
			AWRankingUserDetails details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
			User user = userService.findOne(details.getUserId());
			
			String testactivation_password = null;
			for (EntryPoint entryPoint : user.getEntryPoints()) {
				if (entryPoint.getType() == EntryPointType.EMAIL) {
					testactivation_password = entryPoint.getPassword();
					break;
				}
			}
			if (testactivation_password == null) return getNegativeResponseMap("No current password");

			StateChangeToken stateChangeToken = new StateChangeToken();

			String key = SMTPAuthenticator.getHashed256(form.getEmail() + "." + testactivation_password + "." + request.getLocalAddr());

			stateChangeToken.setToken(key);
			stateChangeToken.setType(StateChangeTokenType.USER_PASSWORD_CHANGE);
			stateChangeToken.setUser(user);
			stateChangeToken.setValue(details.getPassword());
			stateChangeToken.setIpAddress(request.getLocalAddr());//Check later if we need remote or local IP here

			userPasswordChangingService.save(stateChangeToken);

			//-------------- and send link via email -----------------------

			Map<String, Object> params = new HashMap<String, Object>();

			params.put("localAddr", request.getLocalAddr());
			params.put("testactivation_password", testactivation_password);
			params.put("testactivation_email", form.getEmail());

			userPasswordChangingService.send(params);

			return getPositiveResponseMap("password changing link sent to " + form.getEmail());
		
	}
	
	@SuppressWarnings("rawtypes")
	public Map sendPasswordChangingLinkToEmail(UserNewPasswordFormPojo form,Principal principal){
		
		//---------- check if we are logged in user ----------
		
				AWRankingUserDetails details = null;
				if (principal != null) {
					details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
					ArrayList<Role> list = (ArrayList<Role>) details.getAuthorities();

					Boolean isAdmin = false;
					Boolean isUserEmailVerified = false;

					for (Role auth : list) {

						if (auth == Role.ROLE_ADMIN) isAdmin = true;
						if (auth == Role.ROLE_USER_VERIFIED) isUserEmailVerified = true;

					}
					
					if (!isAdmin) {

						if (!isUserEmailVerified) {//if user is not an admin, enter not his email or email is his but not verified

							return getNegativeResponseMap("You are not autorized to change password for user with given email");
						}
					}
				}

				//-------- not logged in or an admin so send the link -------------------

				User user = userService.findOne(details.getUserId());

				StateChangeToken stateChangeToken = new StateChangeToken();

				String key;
				try {
					key = SMTPAuthenticator.getHashed256(user.getEmail() + "." + PasswordUtils.hashPassword(form.getPassword()) + "." + form.getRemoteIP());
				} catch (Exception e) {
					e.printStackTrace();
					return getNegativeResponseMap("You are not autorized to change password for user with given email");
				}

				stateChangeToken.setToken(key);
				stateChangeToken.setType(StateChangeTokenType.USER_PASSWORD_CHANGE);
				stateChangeToken.setUser(user);
				stateChangeToken.setValue(details.getPassword());
				stateChangeToken.setNewValue(PasswordUtils.hashPassword(form.getPassword()));
				stateChangeToken.setIpAddress(form.getRemoteIP());

				try {
					userPasswordChangingService.save(stateChangeToken);
				} catch (AwRankException e) {
				
					e.printStackTrace();
					return getNegativeResponseMap(e.getLocalizedMessage());
				}

				//-------------- and send link via email -----------------------

				Map<String, Object> params = new HashMap<String, Object>();

				params.put("localAddr", form.getRemoteIP());
				params.put("testactivation_password", PasswordUtils.hashPassword(form.getPassword()));
				params.put("testactivation_email", user.getEmail());

				try {
					userPasswordChangingService.send(params);
				} catch (PasswordChangingEmailNotSetException e) {
				
					e.printStackTrace();
					return getNegativeResponseMap(e.getLocalizedMessage());
				}

				return getPositiveResponseMap("password changing link sent to " + user.getEmail());
		
	}
	
	/**
	 * Called from "forgot password" form
	 */
	@SuppressWarnings("rawtypes")
	public Map sendPasswordForgorLinkToEmail(String email, HttpServletRequest request, Principal principal){
		
		//---------- check if we are logged in user ----------
		
		
				AWRankingUserDetails details = null;
				if (principal != null) {
					details = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
					ArrayList<Role> list = (ArrayList<Role>) details.getAuthorities();

					Boolean isAdmin = false;
					Boolean isUserEmailVerified = false;

					for (Role auth : list) {

						if (auth == Role.ROLE_ADMIN) isAdmin = true;
						if (auth == Role.ROLE_USER_VERIFIED) isUserEmailVerified = true;

					}
					
					if (!isAdmin) {

						if(details.getUserEmail().compareTo(email) != 0) return getNegativeResponseMap("FORGOT_PASSWORD_ATTEMPT_TO_CHANGE_WRONG_EMAIL");
						
						if (!isUserEmailVerified) {//if user is not an admin, enter not his email or email is his but not verified

							return getNegativeResponseMap("FORGOT_PASSWORD_NO_VALIDATED_EMAIL");
						}
						
					}
				}

				//-------- not logged in or an admin so send the link -------------------

				User user = userService.findOneByEmail(email);
				if(user.getBanStartedDate() != null) return getNegativeResponseMap("FORGOT_PASSWORD_BLOCKED_USER");

				EntryPoint point = entryPointService.findOneByEntryPointTypeAndUid(EntryPointType.EMAIL, email);
				if(point == null) return getNegativeResponseMap("FORGOT_PASSWORD_NO_VALIDATED_EMAIL");
				
				StateChangeToken stateChangeToken = new StateChangeToken();
				String key;
				
				try {
					key = SMTPAuthenticator.getHashed256(user.getEmail() + "." + point.getPassword() + "." + request.getRemoteAddr());
				} catch (Exception e) {
					e.printStackTrace();
					return getNegativeResponseMap(e.getLocalizedMessage());
				}

				stateChangeToken.setToken(key);
				stateChangeToken.setType(StateChangeTokenType.USER_FORGOT_PASSWORD);
				stateChangeToken.setUser(user);
				stateChangeToken.setValue(point.getPassword());
				stateChangeToken.setIpAddress(request.getRemoteAddr());

				try {
					userPasswordChangingService.save(stateChangeToken);
				} catch (AwRankException e) {
				
					e.printStackTrace();
					return getNegativeResponseMap(e.getLocalizedMessage());
				}

				//-------------- and send link via email -----------------------
				
				Map<String, Object> params = new HashMap<String, Object>();

				params.put("category", forgot_password_xsmtp_header_category);
				params.put("localAddr", request.getRemoteAddr());
				params.put("testactivation_password",point.getPassword());
				params.put("testactivation_email", user.getEmail());

				try {
					userPasswordChangingService.send(params);
				} catch (PasswordChangingEmailNotSetException e) {
				
					e.printStackTrace();
					return getNegativeResponseMap(e.getLocalizedMessage());
				}

				return getPositiveResponseMap("password reset link sent to " + user.getEmail());
	}
	
	
	public  Map updateProfileData(UserProfileDataFormPojo form, Principal principal){
		
		if(principal == null) return getNegativeResponseMap("ERROR_ACCESS");
		
		AWRankingUserDetails details  = (AWRankingUserDetails) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
		
		Long pId = details.getUserId();
		/*
		Long id = form.getUserId();
		if(pId != id) return getNegativeResponseMap("You are logged in as a different user as for what you try to change the data");
		*/
		User user = this.userService.findOne(pId);
		
		if(form.getFirstName() != null) user.setFirstName(form.getFirstName());
		if(form.getLastName() != null) user.setLastName(form.getLastName());
		if(form.getLanguage() != null) user.setLanguage(Language.valueOf(form.getLanguage()));
		if(form.getBirthday() != null) user.setBirthday(form.getBirthday());
		if(form.getSecretAnswer() != null) user.setSecretAnswer(form.getSecretAnswer());
		if(form.getSecretQuestion() != null) user.setSecretQuestionDicCode(form.getSecretQuestion());
		
		this.userService.save(user);
		 
		return getPositiveResponseMap("PROFILE_UPDATED_SUCCESSFULLY");
		
	}
	private Map getPositiveResponseMap() {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "ok");
		return result;
	}

	private Map getPositiveResponseMap(String resultStr) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "ok");
		result.put("reason", resultStr);
		return result;
	}

	private Map getNegativeResponseMap(String reason) {
		Map<String, String> result = new HashMap<String, String>();
		result.put("result", "failure");
		result.put("reason", reason);
		return result;
	}

}
