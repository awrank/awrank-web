package com.awrank.web.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awrank.web.model.dao.EntryPointDao;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.UserRole;
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
	
	/**
	 *  Returns only active! idealy single one OR empty list
	 */
	@Override
	public String findPasswordForUserByEntryPointType(User user, EntryPointType type){
		
		List<EntryPoint> list = entryPointDao.selectActiveByType(user, String.valueOf(type));
		
		if(list == null || list.size() == 0) return null;
		
		return list.get(0).getPassword();
		/*
		List<EntryPoint> filtered = new ArrayList<EntryPoint>();
		
		//It shall be more beautiful way with Collections here but I can't recall it now
		for (EntryPoint ep : list) { if(ep.getEndedDate() == null) filtered.add(ep); }
		
		if(filtered.size() == 0) return null;
		
		return filtered.get(0).getPassword();
		*/
	}

	@Override
	public List<EntryPoint> findEntryPointForUserByEntryPointType(User user, String type) {
		
		return entryPointDao.selectActiveByType(user, String.valueOf(type));
	}

	@Override
	public List<EntryPoint> findEntryPointForUserByEntryPointType(User user, EntryPointType type) {
		
		return entryPointDao.selectActiveByType(user, String.valueOf(type));
	}
	
	@Override
	public List<EntryPoint> findEntryPointForUserByEntryPointTypeAndPassword(User user,
			EntryPointType type, String password) {
		
		return entryPointDao.selectActiveByTypeAndPassword(user, String.valueOf(type), password);
	}
}
