package com.awrank.web.model.dao;

import java.util.List;

import com.awrank.web.model.domain.EntryHistory;
import com.awrank.web.model.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * The {@code EntryHistoryDao} is a data-centric service for the {@link EntryHistory} entity.
 * <p/>
 * It provides the basic methods to get/delete a {@link EntryHistory} instance
 * plus some methods to perform searches (extends {@link PagingAndSortingRepository}).
 *
 * @author Eugene Solomka
 */
public interface EntryHistoryDao extends PagingAndSortingRepository<EntryHistory, Long> {
    /**
     * Returns a {@link Page} of {@link EntryHistory}s belongs to specified user.
     */
    Page<EntryHistory> findByUser(User user, Pageable pageable);
    
    @Query("select e from EntryHistory e where e.ipAddress = :ipAddress")
    List<EntryHistory> findByIP(String ipAddress);
}