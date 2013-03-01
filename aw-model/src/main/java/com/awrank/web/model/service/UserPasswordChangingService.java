package com.awrank.web.model.service;

import com.awrank.web.model.domain.StateChangeToken;
import com.awrank.web.model.exception.emailactivation.UserActivationWasNotVerifiedException;
import com.awrank.web.model.exception.passwordchanging.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Abstract class for password changing service
 *
 * @author Olga Korokhina
 */
@Service
public abstract class UserPasswordChangingService implements StateChangeTokenService {

	public abstract void send(Map params) throws PasswordChangingEmailNotSetException;
	 
	public abstract Boolean verify(String key, HttpServletRequest request) throws PasswordChangeWasNotVerifiedException;//in case some technical problems only!

	public abstract void save(StateChangeToken act);

	public abstract StateChangeToken findByCode(String code);
}
