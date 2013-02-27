package com.awrank.web.model.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.awrank.web.model.exception.entryhistory.*;
import com.awrank.web.model.dao.EntryHistoryDao;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.User;
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

	public Page<EntryHistory> getPageByUser(User user, Pageable pageable){
		
		return (Page<EntryHistory>) entryHistoryDao.findByUser(user, pageable);
	}
	
	@Override
	public  List<EntryHistory> findAllByUser(User user){
		
		return entryHistoryDao.findAllByUser(user);
	}
	
	@Override
	public  List<String> findAllIPByUser(User user){
		
		return entryHistoryDao.findAllIPByUser(user);
	}
}
