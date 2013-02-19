package com.awrank.web.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awrank.web.model.dao.EntryPointDao;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.exception.entrypoint.*;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.domain.User;

/**
 * @author Olga Korokhina
 *
 */
@Service
public class EntryPointServiceImpl implements EntryPointService {

	@Autowired
	private EntryPointDao entryPointDao;
	
	@Override
	public void add(EntryPoint ep) throws EntryPointNotCreatedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(EntryPoint ep) throws EntryPointNotDeletedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void save(EntryPoint ep) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<EntryPoint> findEntryPointForUser(User user) {
		
		return entryPointDao.select(user);
	}

}
