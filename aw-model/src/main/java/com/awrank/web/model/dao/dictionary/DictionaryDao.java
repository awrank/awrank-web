package com.awrank.web.model.dao.dictionary;

import com.awrank.web.model.dao.IAbstractDao;
import com.awrank.web.model.dao.dictionary.wrapper.DictionaryWrapper;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.constant.ELanguage;

import java.util.List;

/**
 * User: a_polyakov
 */
public interface DictionaryDao extends IAbstractDao<Dictionary> {
    public List<DictionaryWrapper> getWrapperList();

    public Dictionary select(ELanguage language, String code);
}
