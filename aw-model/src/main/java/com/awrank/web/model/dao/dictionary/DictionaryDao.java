package com.awrank.web.model.dao.dictionary;

import com.awrank.web.model.dao.AbstractDao;
import com.awrank.web.model.dao.dictionary.wrapper.DictionaryResource;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.constant.ELanguage;

import java.util.List;

/**
 * User: a_polyakov
 */
public interface DictionaryDao extends AbstractDao<Dictionary> {
    public List<DictionaryResource> getWrapperList();

    public Dictionary select(ELanguage language, String code);
}
