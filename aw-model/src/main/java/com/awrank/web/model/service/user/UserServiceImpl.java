package com.awrank.web.model.service.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.awrank.web.model.dao.entrypoint.EntryPointDao;
import com.awrank.web.model.dao.user.UserDao;
import com.awrank.web.model.dao.user.UserDaoImpl;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;

/**
 * @author Olga Korokhina
 *
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private EntryPointDao entryPointDao;
	
	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.user.UserService#add(com.awrank.web.model.domain.User)
	 */
	@Override
	//@Transactional
	public void add(User user) throws UserNotCreatedException{
		
		try{ //can be thrown javax.persistence.PersistenceException etc.
			
			userDao.persist(user);
			//TODO: here save entrance point, verification email and send verification email
		
		}catch(Exception e){
			
			throw new UserNotCreatedException();
			
		}

	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.user.UserService#delete(com.awrank.web.model.domain.User)
	 */
	@Override
	@Transactional
	public void delete(User user) throws UserNotDeletedException{
		
		userDao.remove(user);

	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.user.UserService#save(com.awrank.web.model.domain.User)
	 */
	@Override
	public void save(User user) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.user.UserService#findById(java.lang.Integer)
	 */
	@Override
	public List<User> findById(Long id) {
		// TODO Auto-generated method stub
		return new ArrayList<User>();
	}

	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.user.UserService#findByEmail(java.lang.String)
	 */
	@Override
	public List<User> findByEmail(String email) {
		// TODO Auto-generated method stub
		return new ArrayList<User>();
	}

	@Override
	public List<User> findByAPIKey(String key) {
		// TODO Auto-generated method stub
		return new ArrayList<User>();
	}
	//--------------- check if we need these accessors and refactor them out if not -------
	
	public void setUserDao(UserDaoImpl value){
		
		userDao = value;
	}
	
	public UserDao getUserDao(){
		
		return userDao;
	}
	
	public void setEntryPointDao(EntryPointDao value){
		
		entryPointDao = value;
	}
	
	public EntryPointDao getsetEntryPointDao(){
		
		return entryPointDao;
	}

	
}
