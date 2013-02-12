package com.awrank.web.model.dao;

import com.awrank.web.model.domain.Diary;
import com.awrank.web.model.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * The DiaryDao is a data-centric service for the {@link Diary} entity.
 * <p/>
 * It provides the basic methods to get/delete a {@link Diary} instance
 * plus some methods to perform searches (extends {@link PagingAndSortingRepository}).
 *
 * @author Eugene Solomka
 */
public interface DiaryDao extends PagingAndSortingRepository<Diary, Long> {
    /**
     * Returns a {@link org.springframework.data.domain.Page} of {@link Diary}s belongs to specified user.
     */
    Page<Diary> findByUser(User user, Pageable pageable);
}