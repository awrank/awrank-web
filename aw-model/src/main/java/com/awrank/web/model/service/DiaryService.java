package com.awrank.web.model.service;

import com.awrank.web.model.domain.Diary;
import com.awrank.web.model.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service for saving {@link Diary} records.
 *
 * @author Olga Korokhina
 */
public interface DiaryService {

	/**
	 *
	 * @param diary
	 */
	void save(Diary diary);

	/**
	 *
	 * @param user
	 * @param pageable
	 * @return
	 */
	Page<Diary> findByUser(User user, Pageable pageable);
}
