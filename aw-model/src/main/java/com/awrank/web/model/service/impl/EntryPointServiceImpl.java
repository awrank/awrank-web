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
		entryPointDao.save(ep);
	}

	@Override
	public void delete(EntryPoint ep) throws EntryPointNotDeletedException {
		entryPointDao.delete(ep);

	}

	@Override
	public void save(EntryPoint ep) {
		entryPointDao.save(ep);

	}

	@Override
	public List<EntryPoint> findEntryPointForUser(User user) {
		
		return entryPointDao.select(user);
	}

}
