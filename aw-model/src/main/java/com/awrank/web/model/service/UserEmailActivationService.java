package com.awrank.web.model.service;

import com.awrank.web.model.domain.StateChangeToken;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.emailactivation.UserActivationWasNotVerifiedException;
import com.awrank.web.model.service.impl.AbstarctServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Abstract class for service working with email verification, sending to user's email etc.
 *
 * @author Olga Korokhina
 */
public abstract class UserEmailActivationService extends AbstarctServiceImpl implements StateChangeTokenService {

	public abstract void send(Map params) throws UserActivationEmailNotSetException;

	public abstract Boolean verify(String key, HttpServletRequest request) throws UserActivationWasNotVerifiedException;//in case some technical problems only!

	public abstract void save(StateChangeToken act);

	public abstract StateChangeToken findByCode(String code);

}
