package com.awrank.web.model.service.impl;

import com.awrank.web.model.dao.DictionaryDao;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.exception.ObjectFieldException;
import com.awrank.web.model.exception.ObjectNotUniqueException;
import com.awrank.web.model.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: a_polyakov
 */
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
        return dictionaryDao.save(dictionary);
    }

//
//    @Override
//    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
//    public void update(DictionaryResource wrapper) throws ObjectFieldException, ObjectNotUniqueException {
//        if (wrapper.getId() == null)
//            throw new ObjectFieldException(EMessageConst.MISSING_OBJECT_ID, EObjectType.DICTIONARY, null, null, DictionaryConst.S_ID);
//        if (wrapper.getLanguage() == null)
//            throw new ObjectFieldException(EMessageConst.MISSING_DICTIONARY_LANGUAGE, EObjectType.DICTIONARY, null, wrapper.getId(), DictionaryConst.S_LANGUAGE);
//        if (wrapper.getCode() == null || wrapper.getCode().isEmpty())
//            throw new ObjectFieldException(EMessageConst.MISSING_DICTIONARY_CODE, EObjectType.DICTIONARY, null, wrapper.getId(), DictionaryConst.S_CODE);
//        if (wrapper.getText() == null || wrapper.getText().isEmpty())
//            throw new ObjectFieldException(EMessageConst.MISSING_DICTIONARY_TEXT, EObjectType.DICTIONARY, null, wrapper.getId(), DictionaryConst.S_TEXT);
//
//        Dictionary oldDic = dictionaryDao.select(wrapper.getLanguage(), wrapper.getCode());
//        if (oldDic != null && !oldDic.getId().equals(wrapper.getId()))
//            throw new ObjectNotUniqueException(EObjectType.DICTIONARY, null, wrapper.getId(), null, oldDic.getId());
//        if (oldDic == null) {
//            oldDic = dictionaryDao.find(Dictionary.class, wrapper.getId());
//        }
//        Dictionary dictionary = new Dictionary(wrapper.toJsonObject());
//        dictionary.setCreatedDate(oldDic.getCreatedDate());
//        dictionary.setCreatedBy(oldDic.getCreatedBy());
//        dictionary.setVersion(oldDic.getVersion());
//        dictionaryDao.merge(dictionary);
//    }
//
//    @Override
//    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
//    public void delete(DictionaryResource wrapper) throws ObjectFieldException {
//        if (wrapper.getId() == null)
//            throw new ObjectFieldException(EMessageConst.MISSING_OBJECT_ID, EObjectType.DICTIONARY, null, null, DictionaryConst.S_ID);
//
//        Dictionary dictionary = dictionaryDao.find(Dictionary.class, wrapper.getId());
//        dictionaryDao.remove(dictionary);
//    }
}
