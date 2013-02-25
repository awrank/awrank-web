package com.awrank.web.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.awrank.web.model.dao.EntryHistoryDao;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.UserRole;
import com.awrank.web.model.exception.entrypoint.*;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.EntryPointService;
import com.awrank.web.model.domain.User;

/**
 * @author Olga Korokhina
 *
 */
@Service
public class EntryHistoryServiceImpl implements EntryHistoryService {

	@Autowired
	private EntryHistoryDao entryHistoryDao;
	/*
	@Override
	public void add(EntryHistory ep) throws EntryPointNotCreatedException {
		entryHistoryDao.save(ep);
	}

	@Override
	public void delete(EntryHistory ep) throws EntryPointNotDeletedException {
		entryHistoryDao.delete(ep);

	}
*/
	@Override
	public void save(EntryHistory ep) {
		entryHistoryDao.save(ep);

	}
	@Override
	public List<EntryHistory> findByIP(String ipAddress) {
		
		return entryHistoryDao.findByIP(ipAddress);
	}

	
}
