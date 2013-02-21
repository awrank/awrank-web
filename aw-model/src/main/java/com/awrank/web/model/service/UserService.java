package com.awrank.web.model.service;

import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPOJO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Interface for REST service working with users
 *
 * @author Olga Korokhina
 */
@Service
public interface UserService {

    /**
     * Persist new user, throw exception in case of was not done
     *
     * @param user
     */
    void add(User user) throws UserNotCreatedException;

    /**
     * Delete user, throw exception in case of was not done
     *
     * @param user
     */
    void delete(User user) throws UserNotDeletedException;

    /**
     * Update existing user, throw exception in case of was not done
     *
     * @param user
     */
    void save(User user);

    /**
     * Find user by id, in case of not found returns empty List - no exception thrown, put attention here!!!
     *
     * @param long1
     * @return
     */
    List<User> findById(Long long1);

    /**
     * Find user by email, in case of not found returns empty List - no exception thrown, put attention here!!!
     *
     * @param email
     * @return
     */
    List<User> findByEmail(String email);


    /**
     * Find user by email, in case of not found returns empty List - no exception thrown, put attention here!!!
     *
     * @param email
     * @return
     */
    List<User> findByAPIKey(String key);

    /**
     * On user registration via web form we have to create entry point etc. Password field, coming from form is a
     * part of EntryPoint, not User
     *
     * @param form
     * @return 
     * @throws UserNotCreatedException
     * @throws EntryPointNotCreatedException
     * @throws UserActivationEmailNotSetException
     *
     */
    User register(UserRegistrationFormPOJO form, HttpServletRequest request)
            throws UserNotCreatedException, EntryPointNotCreatedException, UserActivationEmailNotSetException;

}
