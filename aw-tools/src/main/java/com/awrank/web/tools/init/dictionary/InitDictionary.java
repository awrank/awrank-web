package com.awrank.web.tools.init.dictionary;

import com.awrank.web.model.constant.EMessageConst;
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
//    public static void main(String args[]) {
//        EntityManagerFactory emf = Persistence.createEntityManagerFactory("AwrankEMF");
//        final EntityManager em = emf.createEntityManager();
//
//        em.getTransaction().begin();
//
//        UserDaoImpl userDao = new UserDaoImpl();
//        userDao.setEntityManager(em);
//
//        DictionaryDaoImpl dictionaryDao = new DictionaryDaoImpl();
//        dictionaryDao.setEntityManager(em);
//
//        User user = userDao.select("admin@awrank.com");
//        CurrentUserUtils.setCurrentUser(user);
//
//        final List<Dictionary> list = new ArrayList<Dictionary>();
//        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.ERROR.name(), "Ошибка: "));
//        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.ERROR_ACCESS.name(), "У вас нет прав на выполнение операции"));
//        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.ERROR_NETWORK.name(), "Cоединение с сервером отсутствует попробуйте позже"));
//
//        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.MISSING_OBJECT_ID.name(), "Отсутствует идентификатор объекта"));
//        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.OBJECT_NOT_UNIQUE.name(), "Обект должен быть уникален"));
//
//        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.MISSING_DICTIONARY_LANGUAGE.name(), "Отсутствует язык"));
//        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.MISSING_DICTIONARY_CODE.name(), "Отсутствует код"));
//        list.add(new Dictionary(null, ELanguage.RU, EMessageConst.MISSING_DICTIONARY_TEXT.name(), "Отсутствует текст сообщения"));
//
//        list.add(new Dictionary(null, ELanguage.RU, "MY_ACCOUNT", " Учетная запись"));
//        list.add(new Dictionary(null, ELanguage.EN, "MY_ACCOUNT", "My account"));
//        list.add(new Dictionary(null, ELanguage.RU, "LOGIN", "Вход"));
//        list.add(new Dictionary(null, ELanguage.EN, "LOGIN", "Login"));
//        list.add(new Dictionary(null, ELanguage.RU, "LOGOUT", "Выход"));
//        list.add(new Dictionary(null, ELanguage.EN, "LOGOUT", "Logout"));
//        list.add(new Dictionary(null, ELanguage.RU, "FORGET_PASSWORD", "Забыли пароль?"));
//        list.add(new Dictionary(null, ELanguage.EN, "FORGET_PASSWORD", "Forgotten your password?"));
//        list.add(new Dictionary(null, ELanguage.RU, "PASSWORD", "Пароль"));
//        list.add(new Dictionary(null, ELanguage.EN, "PASSWORD", "Password"));
//        list.add(new Dictionary(null, ELanguage.RU, "REGISTRATION", "Регистрация"));
//        list.add(new Dictionary(null, ELanguage.EN, "REGISTRATION", "Registration"));
//        list.add(new Dictionary(null, ELanguage.RU, "PER_MONTH", "в месяц"));
//        list.add(new Dictionary(null, ELanguage.EN, "PER_MONTH", "per month"));
//        list.add(new Dictionary(null, ELanguage.RU, "WITH_SOCIAL_NETWORK", "с помощью социальных сетей:"));
//        list.add(new Dictionary(null, ELanguage.EN, "WITH_SOCIAL_NETWORK", "with social network:"));
//        list.add(new Dictionary(null, ELanguage.RU, "LOGIN_IN_WITH_GOOGLE", "Вход через Google"));
//        list.add(new Dictionary(null, ELanguage.EN, "LOGIN_IN_WITH_GOOGLE", "Log in with Google"));
//        list.add(new Dictionary(null, ELanguage.RU, "LOGIN_IN_WITH_FACEBOOK", "Вход через Facebook"));
//        list.add(new Dictionary(null, ELanguage.EN, "LOGIN_IN_WITH_FACEBOOK", "Log in with Facebook"));
//        list.add(new Dictionary(null, ELanguage.RU, "COUNTRY", "Страна"));
//        list.add(new Dictionary(null, ELanguage.EN, "COUNTRY", "Country"));
//        list.add(new Dictionary(null, ELanguage.RU, "IP_ADDRESS", "IP адрес"));
//        list.add(new Dictionary(null, ELanguage.EN, "IP_ADDRESS", "IP-address"));
//        list.add(new Dictionary(null, ELanguage.RU, "SITE", "САЙТ"));
//        list.add(new Dictionary(null, ELanguage.EN, "SITE", "SITE"));
//        list.add(new Dictionary(null, ELanguage.RU, "HOME", "Главная"));
//        list.add(new Dictionary(null, ELanguage.EN, "HOME", "Home"));
//        list.add(new Dictionary(null, ELanguage.RU, "ACCOUNT", "Учетная запись"));
//        list.add(new Dictionary(null, ELanguage.EN, "ACCOUNT", "ACCOUNT"));
//        list.add(new Dictionary(null, ELanguage.RU, "PROFILE", "Профиль"));
//        list.add(new Dictionary(null, ELanguage.EN, "PROFILE", "Profile"));
//        list.add(new Dictionary(null, ELanguage.RU, "ACTIVITY", "ДЕЯТЕЛЬНОСТЬ"));
//        list.add(new Dictionary(null, ELanguage.EN, "ACTIVITY", "ACTIVITY"));
//        list.add(new Dictionary(null, ELanguage.RU, "REQUEST_HISTORY", "История запросов"));
//        list.add(new Dictionary(null, ELanguage.EN, "REQUEST_HISTORY", "Request history"));
//        list.add(new Dictionary(null, ELanguage.RU, "ADMINISTRATION", "Администрирование"));
//        list.add(new Dictionary(null, ELanguage.EN, "ADMINISTRATION", "Administration"));
//        list.add(new Dictionary(null, ELanguage.RU, "DICTIONARY", "Словарь"));
//        list.add(new Dictionary(null, ELanguage.EN, "DICTIONARY", "Dictionary"));
//
//        list.add(new Dictionary(null, ELanguage.RU, "REGISTER_DESCRIPTION", "или введите email:"));
//        list.add(new Dictionary(null, ELanguage.EN, "REGISTER_DESCRIPTION", "or with email:"));
//        list.add(new Dictionary(null, ELanguage.RU, "REGISTER_FIELD_EMAIL", "Email адрес"));
//        list.add(new Dictionary(null, ELanguage.EN, "REGISTER_FIELD_EMAIL", "Email address"));
//        list.add(new Dictionary(null, ELanguage.RU, "REGISTER_TITLE_SECURITY_QUESTION", "мы рекомендуем установить секретный вопрос и ответ для увеличения безопасности:"));
//        list.add(new Dictionary(null, ELanguage.EN, "REGISTER_TITLE_SECURITY_QUESTION", "we do recommend to set security question and input answer to make account more secure:"));
//        list.add(new Dictionary(null, ELanguage.RU, "REGISTER_FIELD_ANSWER", "Введите ответ здесь..."));
//        list.add(new Dictionary(null, ELanguage.EN, "REGISTER_FIELD_ANSWER", "Input answer here..."));
//        list.add(new Dictionary(null, ELanguage.RU, "REGISTER_TERMS_TITLE", "Я согласен с"));
//        list.add(new Dictionary(null, ELanguage.EN, "REGISTER_TERMS_TITLE", "I agree with the"));
//        list.add(new Dictionary(null, ELanguage.RU, "REGISTER_TERMS_ANCHOR", "Условия использования"));
//        list.add(new Dictionary(null, ELanguage.EN, "REGISTER_TERMS_ANCHOR", "Terms of Service"));
//        list.add(new Dictionary(null, ELanguage.RU, "REGISTER_FOOTER", "У вас есть учетная запись?"));
//        list.add(new Dictionary(null, ELanguage.EN, "REGISTER_FOOTER", "Do you have an account?"));
//
//        list.add(new Dictionary(null, ELanguage.RU, "LOGIN_TITLE", "Вход под своей учетной записью"));
//        list.add(new Dictionary(null, ELanguage.EN, "LOGIN_TITLE", "Log in to your account"));
//        list.add(new Dictionary(null, ELanguage.RU, "LOGIN_DESCRIPTION", "или введите свои данные:"));
//        list.add(new Dictionary(null, ELanguage.EN, "LOGIN_DESCRIPTION", "or with registered details:"));
//        list.add(new Dictionary(null, ELanguage.RU, "LOGIN_FIELD_LOGIN", "Email адрес или логин"));
//        list.add(new Dictionary(null, ELanguage.EN, "LOGIN_FIELD_LOGIN", "Email address or login"));
//        list.add(new Dictionary(null, ELanguage.RU, "LOGIN_FOOTER", "У вас нет учетной записи?"));
//        list.add(new Dictionary(null, ELanguage.EN, "LOGIN_FOOTER", "Don't have an account?"));
//
//        list.add(new Dictionary(null, ELanguage.RU, "FORGET_DESCRIPTION", "Введите адрес электронной почты ниже, и мы вышлем вам инструкцию востановления пароля."));
//        list.add(new Dictionary(null, ELanguage.EN, "FORGET_DESCRIPTION", "Enter your email address below and we'll send you password reset instructions."));
//        list.add(new Dictionary(null, ELanguage.RU, "FORGET_FIELD_EMAIL", "Email адрес"));
//        list.add(new Dictionary(null, ELanguage.EN, "FORGET_FIELD_EMAIL", "Email address"));
//        list.add(new Dictionary(null, ELanguage.RU, "FORGET_BUTTON_SUBMIT", "Востановить пароль"));
//        list.add(new Dictionary(null, ELanguage.EN, "FORGET_BUTTON_SUBMIT", "Reset password"));
//        list.add(new Dictionary(null, ELanguage.RU, "FORGET_FOOTER", "или перейти на"));
//        list.add(new Dictionary(null, ELanguage.EN, "FORGET_FOOTER", "or return to"));
//
//        list.add(new Dictionary(null, ELanguage.RU, "TARIFF_CHOOSE", "Выбрать"));
//        list.add(new Dictionary(null, ELanguage.EN, "TARIFF_CHOOSE", "Choose plan"));
//        list.add(new Dictionary(null, ELanguage.RU, "TARIFF_STARTED_NAME", "Стартовый"));
//        list.add(new Dictionary(null, ELanguage.EN, "TARIFF_STARTED_NAME", "Started"));
//        list.add(new Dictionary(null, ELanguage.RU, "TARIFF_STARTED_DESCRIPTION", "Отлично подходит для веб-мастера, чтобы начать работу"));
//        list.add(new Dictionary(null, ELanguage.EN, "TARIFF_STARTED_DESCRIPTION", "Great for web master to get started"));
//        list.add(new Dictionary(null, ELanguage.RU, "TARIFF_BASIC_NAME", "Базовый"));
//        list.add(new Dictionary(null, ELanguage.EN, "TARIFF_BASIC_NAME", "Basic"));
//        list.add(new Dictionary(null, ELanguage.RU, "TARIFF_BASIC_DESCRIPTION", "Отлично подходит для веб-мастера, чтобы начать работу2"));
//        list.add(new Dictionary(null, ELanguage.EN, "TARIFF_BASIC_DESCRIPTION", "Great for web master to get started2"));
//        list.add(new Dictionary(null, ELanguage.RU, "TARIFF_PRO_NAME", "Профессиональный"));
//        list.add(new Dictionary(null, ELanguage.EN, "TARIFF_PRO_NAME", "Pro"));
//        list.add(new Dictionary(null, ELanguage.RU, "TARIFF_PRO_DESCRIPTION", "Отлично подходит для веб-мастера, чтобы начать работу3"));
//        list.add(new Dictionary(null, ELanguage.EN, "TARIFF_PRO_DESCRIPTION", "Great for web master to get started3"));
//
//        list.add(new Dictionary(null, ELanguage.RU, "SESSION_HISTORY", "История посещений"));
//        list.add(new Dictionary(null, ELanguage.EN, "SESSION_HISTORY", "Session history"));
//        list.add(new Dictionary(null, ELanguage.RU, "SESSION_PAGING_ALL", "все"));
//        list.add(new Dictionary(null, ELanguage.EN, "SESSION_PAGING_ALL", "all"));
//        list.add(new Dictionary(null, ELanguage.RU, "SESSION_TABLE_COLUMN_BROWSE", "Браузер"));
//        list.add(new Dictionary(null, ELanguage.EN, "SESSION_TABLE_COLUMN_BROWSE", "Browse"));
//        list.add(new Dictionary(null, ELanguage.RU, "SESSION_TABLE_COLUMN_LAST_ACTIVE_DATE", "Дата последнего действия"));
//        list.add(new Dictionary(null, ELanguage.EN, "SESSION_TABLE_COLUMN_LAST_ACTIVE_DATE", "Most recent activity"));
//
//
//        for (final Dictionary item : list) {
//            Dictionary oldItem = dictionaryDao.select(item.getLanguage(), item.getCode());
//            if (oldItem == null) {
//                dictionaryDao.persist(item);
//            } else {
//                if (!oldItem.getText().equals(item.getText())) {
//                    oldItem.setText(item.getText());
//                    dictionaryDao.merge(oldItem);
//                }
//            }
//        }
//
//        em.getTransaction().commit();
//        em.close();
//    }
}
