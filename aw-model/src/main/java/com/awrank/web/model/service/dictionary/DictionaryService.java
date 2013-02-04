package com.awrank.web.model.service.dictionary;

import com.awrank.web.model.dao.dictionary.wrapper.DictionaryResource;
import com.awrank.web.model.exception.ObjectFieldException;
import com.awrank.web.model.exception.ObjectNotUniqueException;

import java.util.List;

/**
 * User: a_polyakov
 */
public interface DictionaryService {
    public List<DictionaryResource> getList();

    public DictionaryResource insert(DictionaryResource wrapper) throws ObjectNotUniqueException, ObjectFieldException;

    public void update(DictionaryResource wrapper) throws ObjectFieldException, ObjectNotUniqueException;

    public void delete(DictionaryResource wrapper) throws ObjectFieldException;
}
