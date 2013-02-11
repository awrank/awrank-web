package com.awrank.web.tools.init.dictionary;

import com.awrank.web.model.constant.EMessageConst;
import com.awrank.web.model.dao.dictionary.DictionaryDaoImpl;
import com.awrank.web.model.dao.user.UserDaoImpl;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.User;
import com.awrank.web.model.domain.constant.ELanguage;
import com.awrank.web.model.utils.user.CurrentUserUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

/**
 * Инициализация словаря
 */
public class InitDictionary {
    public static void main(String args[]) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AwrankEMF");
        final EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        UserDaoImpl userDao = new UserDaoImpl();
        userDao.setEntityManager(em);

        DictionaryDaoImpl dictionaryDao = new DictionaryDaoImpl();
        dictionaryDao.setEntityManager(em);

        User user = userDao.select("admin@awrank.com");
        CurrentUserUtils.setCurrentUser(user);

        final List<Dictionary> list = new ArrayList<Dictionary>();
        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.ERROR.name(), "Ошибка: "));
        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.ERROR_ACCESS.name(), "У вас нет прав на выполнение операции"));
        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.ERROR_NETWORK.name(), "Cоединение с сервером отсутствует попробуйте позже"));

        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.MISSING_OBJECT_ID.name(), "Отсутствует идентификатор объекта"));
        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.OBJECT_NOT_UNIQUE.name(), "Обект должен быть уникален"));

        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.MISSING_DICTIONARY_LANGUAGE.name(), "Отсутствует язык"));
        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.MISSING_DICTIONARY_CODE.name(), "Отсутствует код"));
        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.MISSING_DICTIONARY_TEXT.name(), "Отсутствует текст сообщения"));

        list.add(new Dictionary(null, ELanguage.RU, "MY_ACCOUNT", " Учетная запись"));
        list.add(new Dictionary(null, ELanguage.EN, "MY_ACCOUNT", "My account"));
        list.add(new Dictionary(null, ELanguage.RU, "LOGIN", "Вход"));
        list.add(new Dictionary(null, ELanguage.EN, "LOGIN", "Login"));
        list.add(new Dictionary(null, ELanguage.RU, "LOGOUT", "Выход"));
        list.add(new Dictionary(null, ELanguage.EN, "LOGOUT", "Logout"));
        list.add(new Dictionary(null, ELanguage.RU, "FORGET_PASSWORD", "Забыли пароль?"));
        list.add(new Dictionary(null, ELanguage.EN, "FORGET_PASSWORD", "Forgotten your password?"));
        list.add(new Dictionary(null, ELanguage.RU, "PASSWORD", "Пароль"));
        list.add(new Dictionary(null, ELanguage.EN, "PASSWORD", "Password"));
        list.add(new Dictionary(null, ELanguage.RU, "REGISTRATION", "Регистрация"));
        list.add(new Dictionary(null, ELanguage.EN, "REGISTRATION", "Registration"));

        list.add(new Dictionary(null, ELanguage.RU, "LOGIN_TITLE", "Вход под своей учетной записью"));
        list.add(new Dictionary(null, ELanguage.EN, "LOGIN_TITLE", "Log in to your account"));
        list.add(new Dictionary(null, ELanguage.RU, "LOGIN_SOCIAL_DESCRIPTION", "с помощью социальных сетей:"));
        list.add(new Dictionary(null, ELanguage.EN, "LOGIN_SOCIAL_DESCRIPTION", "with social network:"));
        list.add(new Dictionary(null, ELanguage.RU, "LOGIN_SOCIAL_BUTTON_GOOGLE", "Вход через Google"));
        list.add(new Dictionary(null, ELanguage.EN, "LOGIN_SOCIAL_BUTTON_GOOGLE", "Log in with Google"));
        list.add(new Dictionary(null, ELanguage.RU, "LOGIN_SOCIAL_BUTTON_FACEBOOK", "Вход через Facebook"));
        list.add(new Dictionary(null, ELanguage.EN, "LOGIN_SOCIAL_BUTTON_FACEBOOK", "Log in with Facebook"));
        list.add(new Dictionary(null, ELanguage.RU, "LOGIN_DESCRIPTION", "или введите свои данные:"));
        list.add(new Dictionary(null, ELanguage.EN, "LOGIN_DESCRIPTION", "or with registered details:"));
        list.add(new Dictionary(null, ELanguage.RU, "LOGIN_FIELD_LOGIN", "Email адрес или логин"));
        list.add(new Dictionary(null, ELanguage.EN, "LOGIN_FIELD_LOGIN", "Email address or login"));
        list.add(new Dictionary(null, ELanguage.RU, "LOGIN_FOOTER", "У вас нет учетной записи?"));
        list.add(new Dictionary(null, ELanguage.EN, "LOGIN_FOOTER", "Don't have an account?"));

        list.add(new Dictionary(null, ELanguage.RU, "FORGET_DESCRIPTION", "Введите адрес электронной почты ниже, и мы вышлем вам инструкцию востановления пароля."));
        list.add(new Dictionary(null, ELanguage.EN, "FORGET_DESCRIPTION", "Enter your email address below and we'll send you password reset instructions."));
        list.add(new Dictionary(null, ELanguage.RU, "FORGET_FIELD_EMAIL", "Email адрес"));
        list.add(new Dictionary(null, ELanguage.EN, "FORGET_FIELD_EMAIL", "Email address"));
        list.add(new Dictionary(null, ELanguage.RU, "FORGET_BUTTON_SUBMIT", "Востановить пароль"));
        list.add(new Dictionary(null, ELanguage.EN, "FORGET_BUTTON_SUBMIT", "Reset password"));
        list.add(new Dictionary(null, ELanguage.RU, "FORGET_FOOTER", "или перейти на"));
        list.add(new Dictionary(null, ELanguage.EN, "FORGET_FOOTER", "or return to"));


        for (final Dictionary item : list) {
            Dictionary oldItem = dictionaryDao.select(item.getLanguage(), item.getCode());
            if (oldItem == null) {
                dictionaryDao.persist(item);
            } else {
                if (!oldItem.getText().equals(item.getText())) {
                    oldItem.setText(item.getText());
                    dictionaryDao.merge(oldItem);
                }
            }
        }

        em.getTransaction().commit();
        em.close();
    }
}
