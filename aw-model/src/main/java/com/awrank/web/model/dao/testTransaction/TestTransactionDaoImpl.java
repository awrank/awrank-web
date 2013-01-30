package com.awrank.web.model.dao.testTransaction;

import com.awrank.web.model.dao.AbstractDao;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.constant.ELanguage;
import org.springframework.stereotype.Repository;

/**
 *
 */
@Repository
public class TestTransactionDaoImpl extends AbstractDao implements TestTransactionDao {
    @Override
    public void create() {
        Dictionary dictionary = new Dictionary();
        dictionary.setLanguage(ELanguage.RU);
        dictionary.setCode("ERROR2");
        dictionary.setText("Ошибка");
        em.persist(dictionary);
    }
}
