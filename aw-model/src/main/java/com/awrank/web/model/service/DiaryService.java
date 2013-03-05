package com.awrank.web.model.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.awrank.web.model.domain.Diary;
import com.awrank.web.model.domain.User;

/**
 * @author Olga Korokhina
 * Service for saving Diary records
 */

public interface DiaryService {

	public void save(Diary dr);
	
	public Page<Diary> findByUser(User user, Pageable pageable);
}
