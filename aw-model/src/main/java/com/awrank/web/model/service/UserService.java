package com.awrank.web.model.service;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointByEmailNotFoundException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.entrypoint.EntryPointWrongCurrentPasswordException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

/**
 * Interface for REST service working with users.
 *
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 */
public interface UserService extends AbstractService {

	/**
	 * Persist new user, throw exception in case of was not done
	 *
	 * @param user
	 */
	User add(User user) throws UserNotCreatedException;

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
	User findOne(Long long1);

	/**
	 * Find user by email, in case of not found returns empty List - no exception thrown, put attention here!!!
	 *
	 * @param email
	 * @return
	 */
	User findOneByEmail(String email);

	Page<User> pFindOneByEmail(String email, Pageable pageable);

	/**
	 * Find user by email, in case of not found returns empty List - no exception thrown, put attention here!!!
	 *
	 * @param apiKey
	 * @return
	 */
	User findByAPIKey(String apiKey);

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
	EntryPoint register(UserRegistrationFormPojo form, HttpServletRequest request)
			throws UserNotCreatedException, EntryPointNotCreatedException, UserActivationEmailNotSetException;

	List<User> getAll();

	Page<User> pFindAllUsers(Pageable pageable);

	/**
	 * Method for REST URI: /admin/userlist2
	 * @return list of user.
	 */
	List<User> getAllUsers();

	/**
	 *
	 * @param pageable
	 * @return
	 */
	Page<User> getPage(Pageable pageable);

	/**
	 *
	 * @param user
	 * @param principal
	 * @return
	 */
	User blockUser(User user, Principal principal);

	/**
	 *
	 * @param user
	 * @param principal
	 * @return
	 */
	User unblockUser(User user, Principal principal);

	/**
	 * Recovery access account by email, generate and send new password to email
	 *
	 * @param email
	 * @throws EntryPointByEmailNotFoundException
	 *
	 */
	public void recoveryPasswordIntoEmail(String email, String localAddress, String remoteAddress)
			throws EntryPointByEmailNotFoundException;

	/**
	 *
	 * @param uid
	 * @param oldPassword
	 * @param newPassword
	 * @throws EntryPointByEmailNotFoundException
	 * @throws EntryPointWrongCurrentPasswordException
	 */
	public void changePassword(String uid, String oldPassword, String newPassword)
			throws EntryPointByEmailNotFoundException, EntryPointWrongCurrentPasswordException;

	/**
	 * Generates new {@code apiKey} which is not stored in DB yet.
	 * @return {@code apiKey} string value based on {@link UUID}.
	 */
	String getNewApiKey();

}