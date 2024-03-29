package com.awrank.web.model.service;

import com.awrank.web.model.dao.pojos.SessionHistoryFormEntryHistoryPojo;
import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.exception.entryhistory.EntryHistoryNotCreatedException;
import com.awrank.web.model.exception.entryhistory.EntryHistoryNotDeletedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * TODO: JavaDocs
 * Interface for REST service working with entry histories.
 *
 * @author Olga Korokhina
 */
public interface EntryHistoryService extends AbstractService {

	/**
	 * @param entryHistory
	 * @throws EntryHistoryNotCreatedException
	 *
	 */
	void add(EntryHistory entryHistory) throws EntryHistoryNotCreatedException;

	/**
	 * @param entryHistory
	 * @throws EntryHistoryNotDeletedException
	 *
	 */
	void delete(EntryHistory entryHistory) throws EntryHistoryNotDeletedException;

	/**
	 * @param entryHistory
	 */
	void save(EntryHistory entryHistory);

	/**
	 * @param ipAddress
	 * @return
	 */
	List<EntryHistory> findByIP(String ipAddress);

	Page<EntryHistory> pFindByIP(String ipAddress, Pageable pageable);

	/**
	 * @param sessionId
	 * @return
	 */
	List<EntryHistory> findBySessionId(String sessionId);

	Page<EntryHistory> pFindBySessionId(String sessionId, Pageable pageable);

	List<EntryHistory> findAll();

	/**
	 * @param user
	 * @return
	 */
	List<EntryHistory> findAllByUser(User user);

	/**
	 * @param user
	 * @return
	 */
	List<String> findAllIPByUser(User user);

	/**
	 * @param userId
	 * @param pageable
	 * @return
	 */
	Page<EntryHistory> getPageByUserId(Long userId, Pageable pageable);

	/**
	 * @param user
	 * @return
	 */
	EntryHistory getLatestEntryForUser(User user);

	/**
	 * EntryHistoryPojo for SessionHistoryForm
	 *
	 * @param userId
	 * @return
	 */
	public List<SessionHistoryFormEntryHistoryPojo> getSessionHistoryLast100(Long userId);

	public EntryHistory findOneById(Long id);

	/**
	 * logout user set ended EntryHistory
	 *
	 * @param entryHistoryId
	 */
	public void logoutUser(Long entryHistoryId);

	/*----- Pageable -----*/
	Page<EntryHistory> pFindAll(Pageable pageable);

}
