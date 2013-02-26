package com.awrank.web.model.service;

import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.entryhistory.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Olga Korokhina
 *         Interface for REST service working with entry histories
 */
//@Service
public interface EntryHistoryService {

	public void add(EntryHistory ep) throws EntryHistoryNotCreatedException;

	public void delete(EntryHistory ep) throws EntryHistoryNotDeletedException;

	public void save(EntryHistory ep);

	public  List<EntryHistory> findByIP(String ipAddress);
	
	public Page<EntryHistory> getPageByUser(User user, Pageable pageable);
	
}
