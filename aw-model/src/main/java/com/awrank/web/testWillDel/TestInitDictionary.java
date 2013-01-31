package com.awrank.web.testWillDel;

import com.awrank.web.model.constant.EMessageConst;
import com.awrank.web.model.dao.dictionary.DictionaryDaoImpl;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.constant.ELanguage;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Инициализация словаря
 */
public class TestInitDictionary {
    public static void main(String args[]) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AwrankEMF");
        final EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        final List<Dictionary> list = new ArrayList<Dictionary>();
        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.ERROR.name(), "Ошибка: "));
        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.ERROR_ACCESS.name(), "У вас нет прав на выполнение операции"));
        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.ERROR_NETWORK.name(), "Cоединение с сервером отсутствует попробуйте позже"));

        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.MISSING_OBJECT_ID.name(), "Отсутствует идентификатор объекта"));
        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.OBJECT_NOT_UNIQUE.name(), "Обект должен быть уникален"));

        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.MISSING_DICTIONARY_LANGUAGE.name(), "Отсутствует язык"));
        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.MISSING_DICTIONARY_CODE.name(), "Отсутствует код"));
        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.MISSING_DICTIONARY_TEXT.name(), "Отсутствует текст сообщения"));

        DictionaryDaoImpl dictionaryDao = new DictionaryDaoImpl();
        dictionaryDao.setEntityManager(em);

        for (final Dictionary item : list) {
            Dictionary oldItem = dictionaryDao.select(item.getLanguage(), item.getCode());
            if (oldItem == null) {
                em.persist(item);
            } else {
                if (!oldItem.getText().equals(item.getText())) {
                    oldItem.setText(item.getText());
                    em.merge(oldItem);
                }
            }
        }

        em.getTransaction().commit();
        em.close();
    }
}
