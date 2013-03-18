package com.awrank.web.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.awrank.web.model.dao.DiaryDao;
import com.awrank.web.model.domain.Diary;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.service.DiaryService;

/**
 * TODO: Description
 *
 * @author Olga Korokhina
 */
@Service
public class DiaryServiceImpl implements DiaryService {

	@Autowired
	private DiaryDao diaryDao;
	
	/* (non-Javadoc)
	 * @see com.awrank.web.model.service.DiaryService#save(com.awrank.web.model.domain.Diary)
	 */
	@Override
	public void save(Diary diary) {
		diaryDao.save(diary);
	}

	@Override
	public Page<Diary> findByUser(User user, Pageable pageable){
		return diaryDao.findByUser(user, pageable);
	}

}