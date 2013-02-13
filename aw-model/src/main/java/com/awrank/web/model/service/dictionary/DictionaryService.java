package com.awrank.web.model.service.dictionary;

import com.awrank.web.model.dao.dictionary.wrapper.DictionaryResource;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.exception.ObjectFieldException;
import com.awrank.web.model.exception.ObjectNotUniqueException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * User: a_polyakov
 */
public interface DictionaryService {
    Page<Dictionary> findAll(Pageable pageable);

//    public DictionaryResource insert(DictionaryResource wrapper) throws ObjectNotUniqueException, ObjectFieldException;
//
//    public void update(DictionaryResource wrapper) throws ObjectFieldException, ObjectNotUniqueException;
//
//    public void delete(DictionaryResource wrapper) throws ObjectFieldException;
}
