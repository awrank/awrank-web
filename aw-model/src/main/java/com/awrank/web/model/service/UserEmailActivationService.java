package com.awrank.web.model.service;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.StateChangeToken;
import com.awrank.web.model.exception.AwRankException;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.emailactivation.UserActivationWasNotVerifiedException;
import com.awrank.web.model.service.impl.AbstractServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Abstract class for service working with email verification, sending to user's email etc.
 *
 * @author Olga Korokhina
 */
public abstract class UserEmailActivationService extends AbstractServiceImpl implements StateChangeTokenService {

	public abstract void send(Map params) throws UserActivationEmailNotSetException;

	public abstract EntryPoint verify(String key, HttpServletRequest request) throws UserActivationWasNotVerifiedException;//in case some technical problems only!

	public abstract void save(StateChangeToken act) throws AwRankException;

	public abstract StateChangeToken findByCode(String code);

}
