package com.awrank.web.model.service;

import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.entryhistory.EntryHistoryNotCreatedException;
import com.awrank.web.model.exception.entryhistory.EntryHistoryNotDeletedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author Olga Korokhina
 *         Interface for REST service working with entry histories
 */
//@Service
public interface EntryHistoryService extends AbstaractService {

	public void add(EntryHistory ep) throws EntryHistoryNotCreatedException;

	public void delete(EntryHistory ep) throws EntryHistoryNotDeletedException;

	public void save(EntryHistory ep);

	public  List<EntryHistory> findByIP(String ipAddress);
	
	public  List<EntryHistory> findBySessionId(String sessionId);
	
	public  List<EntryHistory> findAllByUser(User user);
	
	public  List<String> findAllIPByUser(User user);
	
	public Page<EntryHistory> getPageByUser(User user, Pageable pageable);

}
