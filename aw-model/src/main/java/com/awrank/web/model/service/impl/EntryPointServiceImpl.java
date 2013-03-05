package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.EntryPointDao;
import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.EntryPointType;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotDeletedException;
import com.awrank.web.model.service.EntryPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * todo: Description
 *
 * @author Olga Korokhina
 */
@Service
public class EntryPointServiceImpl extends AbstractServiceImpl implements EntryPointService {

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
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public EntryPoint findOneByEntryPointTypeAndUid(EntryPointType type, String uid) {
		return entryPointDao.findActiveByTypeAndUid(type, uid);
	}
}
