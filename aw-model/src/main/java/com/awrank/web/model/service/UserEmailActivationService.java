package com.awrank.web.model.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;


import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.emailactivation.UserActivationWasNotVerifiedException;
import com.awrank.web.model.domain.UserEmailActivation;
/**
 * @author Olga Korokhina
 * Interface for service working with email verification, sending to user's email etc.
 */
@Service
public interface UserEmailActivationService {

	public void send(Map params) throws UserActivationEmailNotSetException;
	
	public Boolean verify(String key, HttpServletRequest request) throws UserActivationWasNotVerifiedException;//in case some technical problems only!
	
	public void save(UserEmailActivation act);

	public UserEmailActivation findEmailVerificationByCode(String code);
	
}
