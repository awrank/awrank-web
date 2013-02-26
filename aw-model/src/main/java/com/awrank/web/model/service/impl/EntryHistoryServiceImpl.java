package com.awrank.web.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.awrank.web.model.exception.entryhistory.*;
import com.awrank.web.model.dao.EntryHistoryDao;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.service.EntryHistoryService;

/**
 * @author Olga Korokhina
 *
 */
@Service
public class EntryHistoryServiceImpl implements EntryHistoryService {

	@Autowired
	private EntryHistoryDao entryHistoryDao;
	
	@Override
	public void add(EntryHistory ep) throws EntryHistoryNotCreatedException {
		entryHistoryDao.save(ep);
	}

	@Override
	public void delete(EntryHistory ep) throws EntryHistoryNotDeletedException {
		entryHistoryDao.delete(ep);

	}

	@Override
	public void save(EntryHistory ep) {
		entryHistoryDao.save(ep);

	}
	@Override
	public List<EntryHistory> findByIP(String ipAddress) {
		
		return entryHistoryDao.findByIP(ipAddress);
	}

	
}
