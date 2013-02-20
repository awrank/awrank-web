package com.awrank.web.model.service;

import com.awrank.web.model.domain.UserEmailActivation;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.emailactivation.UserActivationWasNotVerifiedException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Interface for service working with email verification, sending to user's email etc.
 *
 * @author Olga Korokhina
 */
@Service
public interface UserEmailActivationService {

    void send(Map params) throws UserActivationEmailNotSetException;

    Boolean verify(String key, HttpServletRequest request) throws UserActivationWasNotVerifiedException;//in case some technical problems only!

    void save(UserEmailActivation act);

    UserEmailActivation findEmailVerificationByCode(String code);

}
