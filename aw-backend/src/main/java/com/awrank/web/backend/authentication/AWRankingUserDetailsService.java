package com.awrank.web.backend.authentication;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserRoleService;
import com.awrank.web.model.service.UserService;

import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Class for providing custom authentication services, based on Spring Security
 *
 * @author Olga Korokhina
 */
@Service
public class AWRankingUserDetailsService implements UserDetailsService {

	@Autowired 
	@Qualifier("entryPointServiceImpl")
	private EntryPointService entryPointService;

	@Autowired
	@Qualifier("userRoleServiceImpl")
	private UserRoleService userRoleService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	public void setUserService(UserService value) {

		userService = value;
	}

	public UserService getUserService() {

		return userService;
	}

	public void setUserRoleService(UserRoleService value) {

		userRoleService = value;
	}

	public UserRoleService getUserRoleService() {

		return userRoleService;
	}

	public void setEntryPointService(EntryPointService value) {

		entryPointService = value;
	}

	public EntryPointService getEntryPointService() {

		return entryPointService;
	}

	/**
	 * Building a set of getDetailsForUser for given user. As far as we have several entry points with password each
	 *
	 * @param user
	 * @return
	 */
	public List<AWRankingUserDetails> getDetailsForUser(User user) {

		List<AWRankingUserDetails> ud_list = new ArrayList<AWRankingUserDetails>();

		List<EntryPoint> ep_list = entryPointService.findEntryPointForUser(user);

		for (EntryPoint ep : ep_list) {

			AWRankingUserDetails detail = new AWRankingUserDetails(user);
			detail.setType(ep.getType());
			detail.setPassword(ep.getPassword());

			Set<Role> roles = userRoleService.findUserRolesSetForUser(user);

			detail.setRoles(roles);

			ud_list.add(detail);
		}

		return ud_list;
	}

	/**
	 * creates one AWRankingUserDetails by given details, used in manual user authentication after registration
	 *
	 * @param user
	 * @param password
	 * @param type
	 * @return
	 */
	public AWRankingUserDetails createUserDetailsForUserByCredentials(User user, String password, EntryPointType type) {

		AWRankingUserDetails detail = new AWRankingUserDetails(user);
		detail.setPassword(password);
		detail.setType(type);

		Set<Role> roles = getUserRoleService().findUserRolesSetForUser(user);

		detail.setRoles(roles);

		return detail;
	}

	public List<AWRankingUserDetails> getDetailsForUserByEntryPointType(User user, EntryPointType type) {

		List<AWRankingUserDetails> ud_list = new ArrayList<AWRankingUserDetails>();

		List<EntryPoint> ep_list = entryPointService.findEntryPointForUserByType(user, type);

		for (EntryPoint ep : ep_list) {

			AWRankingUserDetails detail = new AWRankingUserDetails(user);
			detail.setType(ep.getType());
			detail.setPassword(ep.getPassword());

			Set<Role> roles = userRoleService.findUserRolesSetForUser(user);

			detail.setRoles(roles);

			ud_list.add(detail);
		}

		return ud_list;
	}

	/*
	 *
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username)//can be login or email
			throws UsernameNotFoundException {

		User user = userService.findOneByEmail(username);
		List details = getDetailsForUser(user);
		if(details == null || details.size() == 0) return new AWRankingUserDetails(user);
		
		return getDetailsForUser(user).get(0);
	}

	public UserDetails loadUserByUsernameAndPassword(String username, Object credentials) throws BadCredentialsException{
		//TODO: here check if this is email or login and select different types of entry point
		User user = userService.findOneByEmail(username);
		
		if(user == null) throw new BadCredentialsException("User not found");
		
		List<AWRankingUserDetails> ud_list = new ArrayList<AWRankingUserDetails>();
		
		List<EntryPoint> ep_list = entryPointService.findEntryPointForUserByTypeAndPassword(user, EntryPointType.EMAIL, String.valueOf(credentials));
		
		for (EntryPoint ep : ep_list) {

			AWRankingUserDetails detail = new AWRankingUserDetails(user);
			detail.setType(ep.getType());
			detail.setPassword(ep.getPassword());

			Set<Role> roles = userRoleService.findUserRolesSetForUser(user);

			detail.setRoles(roles);

			ud_list.add(detail);
		}

		if(ud_list == null || ud_list.size() == 0){
			//------- log the bad login access --------------
			
			LocalDateTime time  = LocalDateTime.now();
	         
	         Integer acc = user.getAuthorizationFailsCount();
	         if(acc == null) acc = 0;
	         acc++;
	         user.setAuthorizationFailsCount(acc);
	         user.setAuthorizationFailsLastDate(time);
	         
	         userService.save(user);
	         
			throw new BadCredentialsException("Wrong password");
		}
		
		return ud_list.get(0);
	}
}
