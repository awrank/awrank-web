package com.awrank.web.model.service.impl;


import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.service.UserProfileService;
import com.awrank.web.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Olga Korokhina
 */
@Service
public class UserProfileServiceImpl extends AbstractServiceImpl implements UserProfileService {

	@Autowired
	private UserService userService;

	@Autowired
	private EntryPointService entryPointService;

	@Autowired
	private EntryHistoryService entryHistoryService;

	public UserProfileServiceImpl() {
	}

	//@Override
	//@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	
}
