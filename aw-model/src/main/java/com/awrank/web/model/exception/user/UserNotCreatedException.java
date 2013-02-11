/**
 * 
 */
package com.awrank.web.model.exception.user;

import com.awrank.web.model.exception.AwRankException;

/**
 * Thrown in case of UserServiceImpl#add was not executed
 * 
 * @author Olga Korokhina
 *
 */
@SuppressWarnings("serial")
public class UserNotCreatedException extends AwRankException {
	
	public UserNotCreatedException(){
		
	}
	
	@Override 
	public String getMessage(){
		
		return "New user was not created";
	}

}
