package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.DictionaryDao;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.exception.ObjectFieldException;
import com.awrank.web.model.exception.ObjectNotUniqueException;
import com.awrank.web.model.service.DictionaryService;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DictionaryServiceImpl implements DictionaryService {
    @Autowired
    private DictionaryDao dictionaryDao;

    @Transactional(readOnly = true)
    public  Page<Dictionary> findAll(Pageable pageable) {
        return dictionaryDao.findAll(pageable);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Dictionary create(Dictionary dictionary) throws ObjectNotUniqueException, ObjectFieldException {
        if (StringUtils.isEmptyOrWhitespaceOnly(dictionary.getCode()))
            throw new ObjectFieldException();

        if (StringUtils.isEmptyOrWhitespaceOnly(dictionary.getText()))
            throw new ObjectFieldException();

        Dictionary existing = dictionaryDao.findByCodeAndLanguage(dictionary.getCode(), dictionary.getLanguage());

        if (existing != null)
            throw new DuplicateKeyException("");

        return dictionaryDao.save(dictionary);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Dictionary update(Dictionary dictionary) throws ObjectFieldException {
        Dictionary existing = dictionaryDao.findOne(dictionary.getId());

        if (existing == null)
            throw new ObjectFieldException();

        existing.setCode(dictionary.getCode());
        existing.setText(dictionary.getText());
        existing.setLanguage(dictionary.getLanguage());

        return dictionaryDao.save(dictionary);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        dictionaryDao.delete(id);
    }
}