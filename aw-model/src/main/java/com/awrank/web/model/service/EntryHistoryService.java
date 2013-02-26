package com.awrank.web.model.service;

import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.exception.entryhistory.*;

import java.util.List;

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
	
}
