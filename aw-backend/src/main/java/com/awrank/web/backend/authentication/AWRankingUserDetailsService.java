package com.awrank.web.backend.authentication;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserRoleService;
import com.awrank.web.model.service.UserService;

/**
 * Class for providing custom authentication services, based on Spring Security
 * 
 * @author Olga Korokhina
 *
 */
@Service
public class AWRankingUserDetailsService implements UserDetailsService {

	@Autowired 
	private EntryPointService entryPointService;
	
	@Autowired 
	private UserRoleService userRoleService;
	
	@Autowired 
	private UserService userService;
	
	public void setUserService(UserService value){
		
		userService = value;
	}
	
	public UserService getUserService(){
		
		return userService;
	}

	/**
	 *  Building a set of getDetailsForUser for given user. As far as we have several entry points with password each
	 *  
	 * @param user
	 * @return
	 */
	public List<AWRankingUserDetails> getDetailsForUser(User user){
		
		List<AWRankingUserDetails>  ud_list= new ArrayList<AWRankingUserDetails>();
		
		List<EntryPoint> ep_list = entryPointService.findEntryPointForUser(user);
		
		for (EntryPoint ep : ep_list){
			
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
	 *  creates one AWRankingUserDetails by given details, used in manual user authentication after registration
	 * @param user
	 * @param password
	 * @param type
	 * @return
	 */
	public AWRankingUserDetails createUserDetailsForUserByCredentials(User user, String password, EntryPointType type){
		
		AWRankingUserDetails detail = new AWRankingUserDetails(user);
		detail.setPassword(password);
		detail.setType(type);
		
		Set<Role> roles = userRoleService.findUserRolesSetForUser(user);
		
		detail.setRoles(roles);
		
		return detail;
	}
	
	public List<AWRankingUserDetails> getDetailsForUserByEntryPointType(User user, EntryPointType type){
		
		List<AWRankingUserDetails>  ud_list= new ArrayList<AWRankingUserDetails>();
		
		List<EntryPoint> ep_list = entryPointService.findEntryPointForUserByEntryPointType(user, type);
		
		for (EntryPoint ep : ep_list){
			
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
	 * TODO: implement more intelligent
	 * @see org.springframework.security.core.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	@Override
	public UserDetails loadUserByUsername(String username)//can be login or email
			throws UsernameNotFoundException {
		
		List<User> list = userService.findByEmail(username);
		
		
		return getDetailsForUser(list.get(0)).get(0);
	}

}
