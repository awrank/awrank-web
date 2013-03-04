package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.EntryPointDao;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotDeletedException;
import com.awrank.web.model.service.EntryPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * todo: Description
 *
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
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
	 * Returns only active! ideally single one OR empty list
	 */
	@Override
	public String findPasswordForUserByType(User user, EntryPointType type) {
		List<EntryPoint> list = entryPointDao.selectActiveByType(user, type);
		if (list == null || list.size() == 0) {
			return null;
		}
		return list.get(0).getPassword();
	}

	@Override
	public List<EntryPoint> findEntryPointForUserByType(User user, EntryPointType type) {
		return entryPointDao.selectActiveByType(user, type);
	}

	@Override
	public List<EntryPoint> findEntryPointForUserByTypeAndPassword(User user, EntryPointType type, String password) {
		return entryPointDao.selectActiveByTypeAndPassword(user, type, password);
	}

	@Override
	public List<EntryPoint> findForUserByTypeAndUID(User user, EntryPointType type, String uid) {
		return entryPointDao.selectActiveByTypeAndUID(user, type, uid);
	}

}
