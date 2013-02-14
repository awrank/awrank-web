/**
 * 
 */
package com.awrank.web.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;

/**
 * @author Olga Korokhina
 * Interface for REST service working with users
 */
@Service
public interface UserService {
	
	/**
	 * Persist new user, throw exception in case of was not done
	 * 
	 * @param user
	 */
	public void add(User user) throws UserNotCreatedException;
	
	/**
	 * Delete user, throw exception in case of was not done
	 * 
	 * @param user
	 */
	public void delete(User user) throws UserNotDeletedException;
	
	/**
	 * Update existing user, throw exception in case of was not done
	 * 
	 * @param user
	 */
	public void save(User user);
	
	/**
	 * Find user by id, in case of not found returns empty List - no exception thrown, put attention here!!!
	 * 
	 * @param long1
	 * @return
	 */
	public List<User> findById(Long long1);
	
	/**
	 * Find user by email, in case of not found returns empty List - no exception thrown, put attention here!!!
	 * 
	 * @param email
	 * @return
	 */
	public List<User> findByEmail(String email);
	
	
	/**
	 * Find user by email, in case of not found returns empty List - no exception thrown, put attention here!!!
	 * 
	 * @param email
	 * @return
	 */
	public List<User> findByAPIKey(String key);

	/**
	 *  On user registration via web form we have to create entry point etc. Password field, coming from form is a 
	 *  part of EntryPoint, not User
	 * 
	 * @param form
	 * @throws UserNotCreatedException 
	 */
	public void register(UserRegistrationFormPojo form) throws UserNotCreatedException; 
}
