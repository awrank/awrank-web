package com.awrank.web.model.dao;

import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * The {@code EntryHistoryDao} is a data-centric service for the {@link EntryHistory} entity.
 * <p/>
 * It provides the basic methods to get/delete a {@link EntryHistory} instance
 * plus some methods to perform searches (extends {@link PagingAndSortingRepository}).
 *
 * @author Eugene Solomka
 * @author Olga Korokhina
 */
public interface EntryHistoryDao extends PagingAndSortingRepository<EntryHistory, Long> {
	/**
	 * Returns a {@link Page} of {@link EntryHistory}s belongs to specified user.
	 */
	// TODO проверить
	@Query("select e from EntryHistory e where e.user.id = :userId")
	Page<EntryHistory> findByUserId(@Param("userId") Long userId, Pageable pageable);

	@Query("select e from EntryHistory e where e.sessionId = :sessionId")
	List<EntryHistory> findBySessionId(@Param("sessionId") String sessionId);

	@Query("select e from EntryHistory e where e.sessionId like :sessionId")
	Page<EntryHistory> pFindBySessionId(@Param("sessionId") String sessionId, Pageable pageable);

	@Query("select e from EntryHistory e where e.ipAddress = :ipAddress")
	List<EntryHistory> findByIP(@Param("ipAddress") String ipAddress);

	@Query("select e from EntryHistory e where e.ipAddress = :ipAddress")
	Page<EntryHistory> pFindByIpAddress(@Param("ipAddress") String ipAddress, Pageable pageable);

	@Query("select e from EntryHistory e where e.user = :user")
	List<EntryHistory> findAllByUser(@Param("user") User user);

	@Query("select DISTINCT e.ipAddress from EntryHistory e where e.user = :user")
	List<String> findAllIPByUser(@Param("user") User user);
	
	@Query("select e from EntryHistory e where e.user = :user order by 'sign_in_date' desc limit 1")
	List<EntryHistory> findLatestEntryForUser(@Param("user") User user);

}