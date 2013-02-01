package com.awrank.web.model.dao.dictionary;

import com.awrank.web.model.dao.AbstractDaoImpl;
import com.awrank.web.model.dao.dictionary.wrapper.DictionaryWrapper;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.constant.ELanguage;
import com.awrank.web.model.utils.select.SelectUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.List;

/**
 * User: a_polyakov
 */
@Repository
public class DictionaryDaoImpl extends AbstractDaoImpl<Dictionary> implements DictionaryDao {
    @Override
    public List<DictionaryWrapper> getWrapperList() {
        final List list = SelectUtils.getWrapperList(em, DictionaryWrapper.class, " order by o." + Dictionary.H_LANGUAGE + ", o." + Dictionary.H_CODE, 0, 0);
        return list;
    }

    private static final String HQ_SELECT = "select o from " + Dictionary.class.getSimpleName() + " o " +
            "where o." + Dictionary.H_LANGUAGE + "=?1 and o." + Dictionary.H_CODE + "=?2";

    @Override
    public Dictionary select(ELanguage language, String code) {
        Query query = em.createQuery(HQ_SELECT);
        query.setParameter(1, language);
        query.setParameter(2, code);
        Dictionary dictionary = null;
        try {
            dictionary = (Dictionary) query.getSingleResult();
        } catch (NoResultException e) {
        }
        return dictionary;
    }
}
