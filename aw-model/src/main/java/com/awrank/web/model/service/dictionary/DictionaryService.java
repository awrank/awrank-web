package com.awrank.web.model.service.dictionary;

import com.awrank.web.model.dao.dictionary.wrapper.DictionaryWrapper;
import com.awrank.web.model.exception.ObjectFieldException;
import com.awrank.web.model.exception.ObjectNotUniqueException;

import java.util.List;

/**
 * User: a_polyakov
 */
public interface DictionaryService {
    public List<DictionaryWrapper> getList();

    public DictionaryWrapper insert(DictionaryWrapper wrapper) throws ObjectNotUniqueException, ObjectFieldException;

    public void update(DictionaryWrapper wrapper) throws ObjectFieldException, ObjectNotUniqueException;

    public void delete(DictionaryWrapper wrapper) throws ObjectFieldException;
}
