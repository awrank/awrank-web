package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.EntryHistoryCustomDao;
import com.awrank.web.model.dao.EntryHistoryDao;
import com.awrank.web.model.dao.pojos.SessionHistoryFormEntryHistoryPojo;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.entryhistory.EntryHistoryNotCreatedException;
import com.awrank.web.model.exception.entryhistory.EntryHistoryNotDeletedException;
import com.awrank.web.model.service.EntryHistoryService;
import org.joda.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * TODO: JavaDoc
 *
 * @author Olga Korokhina
 */
@Service
public class EntryHistoryServiceImpl extends AbstractServiceImpl implements EntryHistoryService {

	@Autowired
	private EntryHistoryDao entryHistoryDao;

	@Autowired
	private EntryHistoryCustomDao entryHistoryCustomDao;

	@Override
	public void add(EntryHistory entryHistory) throws EntryHistoryNotCreatedException {
		entryHistoryDao.save(entryHistory);
	}

	@Override
	public void delete(EntryHistory entryHistory) throws EntryHistoryNotDeletedException {
		entryHistoryDao.delete(entryHistory);
	}

	@Override
	public void save(EntryHistory entryHistory) {
		entryHistoryDao.save(entryHistory);
	}

	@Override
	public List<EntryHistory> findByIP(String ipAddress) {
		return entryHistoryDao.findByIP(ipAddress);
	}

	@Override
	public Page<EntryHistory> pFindByIP(String ipAddress, Pageable pageable) {
		return entryHistoryDao.pFindByIpAddress(ipAddress, pageable);
	}

	public Page<EntryHistory> getPageByUserId(Long userId, Pageable pageable) {
		return (Page<EntryHistory>) entryHistoryDao.findByUserId(userId, pageable);
	}

	@Override
	public List<EntryHistory> findAll() {
		return (List<EntryHistory>) entryHistoryDao.findAll();
	}

	@Override
	public List<EntryHistory> findAllByUser(User user) {
		return entryHistoryDao.findAllByUser(user);
	}

	@Override
	public List<String> findAllIPByUser(User user) {
		return entryHistoryDao.findAllIPByUser(user);
	}

	@Override
	public List<EntryHistory> findBySessionId(String sessionId) {
		return entryHistoryDao.findBySessionId(sessionId);
	}

	@Override
	public Page<EntryHistory> pFindBySessionId(String sessionId, Pageable pageable) {
		return entryHistoryDao.pFindBySessionId(sessionId, pageable);
	}

	@Override
	public EntryHistory getLatestEntryForUser(User user) {
		return (entryHistoryDao.findLatestEntryForUser(user)).get(0);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<SessionHistoryFormEntryHistoryPojo> getSessionHistoryLast100(Long userId) {
		List<SessionHistoryFormEntryHistoryPojo> list = entryHistoryCustomDao.getSessionHistoryLast100(userId);
		return list;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public EntryHistory findOneById(Long id) {
		EntryHistory entryHistory = entryHistoryDao.findOne(id);
		return entryHistory;
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void logoutUser(Long entryHistoryId) {
		EntryHistory entryHistory = entryHistoryDao.findOne(entryHistoryId);
		if (entryHistory != null) {
			LocalDateTime now = LocalDateTime.now();
			entryHistory.setEndedDate(now);
			entryHistory.setSignoutDate(now);
			entryHistoryDao.save(entryHistory);
		}
	}


	/*----- Pageable -----*/
	public Page<EntryHistory> pFindAll(Pageable pageable) {
		return entryHistoryDao.findAll(pageable);
	}

}
