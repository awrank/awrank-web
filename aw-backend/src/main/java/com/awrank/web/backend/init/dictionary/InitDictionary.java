package com.awrank.web.backend.init.dictionary;

import com.awrank.web.model.dao.DictionaryDao;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.Language;
import com.awrank.web.model.enums.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Инициализация словаря
 */
@Service
public class InitDictionary {

	@Autowired
	private DictionaryDao dictionaryDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void init() {
		final List<Dictionary> list = new ArrayList<Dictionary>();
////        ERROR
		list.add(new Dictionary(null, Language.RU, Message.ERROR.name(), "Ошибка: "));
		list.add(new Dictionary(null, Language.RU, Message.ERROR_ACCESS.name(), "У вас нет прав на выполнение операции"));
		list.add(new Dictionary(null, Language.RU, Message.ERROR_NETWORK.name(), "Cоединение с сервером отсутствует попробуйте позже"));
//
		list.add(new Dictionary(null, Language.RU, Message.MISSING_OBJECT_ID.name(), "Отсутствует идентификатор объекта"));
		list.add(new Dictionary(null, Language.RU, Message.OBJECT_NOT_UNIQUE.name(), "Обект должен быть уникален"));

		list.add(new Dictionary(null, Language.RU, Message.MISSING_DICTIONARY_LANGUAGE.name(), "Отсутствует язык"));
		list.add(new Dictionary(null, Language.RU, Message.MISSING_DICTIONARY_CODE.name(), "Отсутствует код"));
		list.add(new Dictionary(null, Language.RU, Message.MISSING_DICTIONARY_TEXT.name(), "Отсутствует текст сообщения"));

////        simple words
		list.add(new Dictionary(null, Language.RU, "COUNTRY", "Страна"));
		list.add(new Dictionary(null, Language.EN, "COUNTRY", "Country"));
		list.add(new Dictionary(null, Language.RU, "DELETE", "Удалить"));
		list.add(new Dictionary(null, Language.EN, "DELETE", "Delete"));
		list.add(new Dictionary(null, Language.RU, "DICTIONARY", "Словарь"));
		list.add(new Dictionary(null, Language.EN, "DICTIONARY", "Dictionary"));
		list.add(new Dictionary(null, Language.RU, "INSERT", "Добавить"));
		list.add(new Dictionary(null, Language.EN, "INSERT", "Insert"));
		list.add(new Dictionary(null, Language.RU, "IP_ADDRESS", "IP адрес"));
		list.add(new Dictionary(null, Language.EN, "IP_ADDRESS", "IP-address"));
		list.add(new Dictionary(null, Language.RU, "LANGUAGE", "Язык"));
		list.add(new Dictionary(null, Language.EN, "LANGUAGE", "Language"));
		list.add(new Dictionary(null, Language.RU, "LOGIN", "Вход"));
		list.add(new Dictionary(null, Language.EN, "LOGIN", "Login"));
		list.add(new Dictionary(null, Language.RU, "LOGOUT", "Выход"));
		list.add(new Dictionary(null, Language.EN, "LOGOUT", "Logout"));
		list.add(new Dictionary(null, Language.RU, "PASSWORD", "Пароль"));
		list.add(new Dictionary(null, Language.EN, "PASSWORD", "Password"));
		list.add(new Dictionary(null, Language.RU, "PASSWORD_CONFIRM", "Подтвердите пароль"));
		list.add(new Dictionary(null, Language.EN, "PASSWORD_CONFIRM", "Password confirm"));
		list.add(new Dictionary(null, Language.RU, "PAYMENT", "Оплата"));
		list.add(new Dictionary(null, Language.EN, "PAYMENT", "Payment"));
		list.add(new Dictionary(null, Language.RU, "SITE", "САЙТ"));
		list.add(new Dictionary(null, Language.EN, "SITE", "SITE"));
		list.add(new Dictionary(null, Language.RU, "UPDATE", "Обновить"));
		list.add(new Dictionary(null, Language.EN, "UPDATE", "Update"));
		list.add(new Dictionary(null, Language.RU, "ORDER", "Заказ"));
		list.add(new Dictionary(null, Language.EN, "ORDER", "Order"));
		list.add(new Dictionary(null, Language.RU, "MORE", "еще"));
		list.add(new Dictionary(null, Language.EN, "MORE", "more"));
//      ENTITY
		list.add(new Dictionary(null, Language.RU, "DICTIONARY_CODE", "Код"));
		list.add(new Dictionary(null, Language.EN, "DICTIONARY_CODE", "Code"));
		list.add(new Dictionary(null, Language.RU, "DICTIONARY_TEXT", "Текст"));
		list.add(new Dictionary(null, Language.EN, "DICTIONARY_TEXT", "Text"));

////      UI
		list.add(new Dictionary(null, Language.RU, "MY_ACCOUNT", " Учетная запись"));
		list.add(new Dictionary(null, Language.EN, "MY_ACCOUNT", "My account"));

		list.add(new Dictionary(null, Language.RU, "FORGET_PASSWORD", "Забыли пароль?"));
		list.add(new Dictionary(null, Language.EN, "FORGET_PASSWORD", "Forgotten your password?"));

		list.add(new Dictionary(null, Language.RU, "REGISTRATION", "Регистрация"));
		list.add(new Dictionary(null, Language.EN, "REGISTRATION", "Registration"));
		list.add(new Dictionary(null, Language.RU, "PER_MONTH", "в месяц"));
		list.add(new Dictionary(null, Language.EN, "PER_MONTH", "per month"));
		list.add(new Dictionary(null, Language.RU, "WITH_SOCIAL_NETWORK", "с помощью социальных сетей:"));
		list.add(new Dictionary(null, Language.EN, "WITH_SOCIAL_NETWORK", "with social network:"));
		list.add(new Dictionary(null, Language.RU, "LOGIN_IN_WITH_GOOGLE", "Вход через Google"));
		list.add(new Dictionary(null, Language.EN, "LOGIN_IN_WITH_GOOGLE", "Log in with Google"));
		list.add(new Dictionary(null, Language.RU, "LOGIN_IN_WITH_FACEBOOK", "Вход через Facebook"));
		list.add(new Dictionary(null, Language.EN, "LOGIN_IN_WITH_FACEBOOK", "Log in with Facebook"));

		list.add(new Dictionary(null, Language.RU, "HOME", "Главная"));
		list.add(new Dictionary(null, Language.EN, "HOME", "Home"));
		list.add(new Dictionary(null, Language.RU, "ACCOUNT", "Учетная запись"));
		list.add(new Dictionary(null, Language.EN, "ACCOUNT", "ACCOUNT"));
		list.add(new Dictionary(null, Language.RU, "PROFILE", "Профиль"));
		list.add(new Dictionary(null, Language.EN, "PROFILE", "Profile"));
		list.add(new Dictionary(null, Language.RU, "ACTIVITY", "ДЕЯТЕЛЬНОСТЬ"));
		list.add(new Dictionary(null, Language.EN, "ACTIVITY", "ACTIVITY"));
		list.add(new Dictionary(null, Language.RU, "REQUEST_HISTORY", "История запросов"));
		list.add(new Dictionary(null, Language.EN, "REQUEST_HISTORY", "Request history"));
		list.add(new Dictionary(null, Language.RU, "ADMINISTRATION", "Администрирование"));
		list.add(new Dictionary(null, Language.EN, "ADMINISTRATION", "Administration"));

		list.add(new Dictionary(null, Language.RU, "REGISTER_DESCRIPTION", "или введите email:"));
		list.add(new Dictionary(null, Language.EN, "REGISTER_DESCRIPTION", "or with email:"));
		list.add(new Dictionary(null, Language.RU, "REGISTER_FIELD_EMAIL", "Email адрес"));
		list.add(new Dictionary(null, Language.EN, "REGISTER_FIELD_EMAIL", "Email address"));
		list.add(new Dictionary(null, Language.RU, "REGISTER_TITLE_SECURITY_QUESTION", "мы рекомендуем установить секретный вопрос и ответ для увеличения безопасности:"));
		list.add(new Dictionary(null, Language.EN, "REGISTER_TITLE_SECURITY_QUESTION", "we do recommend to set security question and input answer to make account more secure:"));
		list.add(new Dictionary(null, Language.RU, "REGISTER_FIELD_ANSWER", "Введите ответ здесь..."));
		list.add(new Dictionary(null, Language.EN, "REGISTER_FIELD_ANSWER", "Input answer here..."));
		list.add(new Dictionary(null, Language.RU, "REGISTER_TERMS_TITLE", "Я согласен с"));
		list.add(new Dictionary(null, Language.EN, "REGISTER_TERMS_TITLE", "I agree with the"));
		list.add(new Dictionary(null, Language.RU, "REGISTER_TERMS_ANCHOR", "Условия использования"));
		list.add(new Dictionary(null, Language.EN, "REGISTER_TERMS_ANCHOR", "Terms of Service"));
		list.add(new Dictionary(null, Language.RU, "REGISTER_FOOTER", "У вас есть учетная запись?"));
		list.add(new Dictionary(null, Language.EN, "REGISTER_FOOTER", "Do you have an account?"));

		list.add(new Dictionary(null, Language.RU, "LOGIN_TITLE", "Вход под своей учетной записью"));
		list.add(new Dictionary(null, Language.EN, "LOGIN_TITLE", "Log in to your account"));
		list.add(new Dictionary(null, Language.RU, "LOGIN_DESCRIPTION", "или введите свои данные:"));
		list.add(new Dictionary(null, Language.EN, "LOGIN_DESCRIPTION", "or with registered details:"));
		list.add(new Dictionary(null, Language.RU, "LOGIN_FIELD_LOGIN", "Email адрес или логин"));
		list.add(new Dictionary(null, Language.EN, "LOGIN_FIELD_LOGIN", "Email address or login"));
		list.add(new Dictionary(null, Language.RU, "LOGIN_FOOTER", "У вас нет учетной записи?"));
		list.add(new Dictionary(null, Language.EN, "LOGIN_FOOTER", "Don't have an account?"));

		list.add(new Dictionary(null, Language.RU, "FORGET_DESCRIPTION", "Введите адрес электронной почты ниже, и мы вышлем вам инструкцию востановления пароля."));
		list.add(new Dictionary(null, Language.EN, "FORGET_DESCRIPTION", "Enter your email address below and we'll send you password reset instructions."));
		list.add(new Dictionary(null, Language.RU, "FORGET_FIELD_EMAIL", "Email адрес"));
		list.add(new Dictionary(null, Language.EN, "FORGET_FIELD_EMAIL", "Email address"));
		list.add(new Dictionary(null, Language.RU, "FORGET_BUTTON_SUBMIT", "Востановить пароль"));
		list.add(new Dictionary(null, Language.EN, "FORGET_BUTTON_SUBMIT", "Reset password"));
		list.add(new Dictionary(null, Language.RU, "FORGET_FOOTER", "или перейти на"));
		list.add(new Dictionary(null, Language.EN, "FORGET_FOOTER", "or return to"));

		list.add(new Dictionary(null, Language.RU, "TARIFF_CHOOSE", "Выбрать"));
		list.add(new Dictionary(null, Language.EN, "TARIFF_CHOOSE", "Choose plan"));
		list.add(new Dictionary(null, Language.RU, "TARIFF_STARTED_NAME", "Стартовый"));
		list.add(new Dictionary(null, Language.EN, "TARIFF_STARTED_NAME", "Started"));
		list.add(new Dictionary(null, Language.RU, "TARIFF_STARTED_DESCRIPTION", "Отлично подходит для веб-мастера, чтобы начать работу"));
		list.add(new Dictionary(null, Language.EN, "TARIFF_STARTED_DESCRIPTION", "Great for web master to get started"));
		list.add(new Dictionary(null, Language.RU, "TARIFF_BASIC_NAME", "Базовый"));
		list.add(new Dictionary(null, Language.EN, "TARIFF_BASIC_NAME", "Basic"));
		list.add(new Dictionary(null, Language.RU, "TARIFF_BASIC_DESCRIPTION", "Отлично подходит для веб-мастера, чтобы начать работу2"));
		list.add(new Dictionary(null, Language.EN, "TARIFF_BASIC_DESCRIPTION", "Great for web master to get started2"));
		list.add(new Dictionary(null, Language.RU, "TARIFF_PRO_NAME", "Профессиональный"));
		list.add(new Dictionary(null, Language.EN, "TARIFF_PRO_NAME", "Pro"));
		list.add(new Dictionary(null, Language.RU, "TARIFF_PRO_DESCRIPTION", "Отлично подходит для веб-мастера, чтобы начать работу3"));
		list.add(new Dictionary(null, Language.EN, "TARIFF_PRO_DESCRIPTION", "Great for web master to get started"));
		list.add(new Dictionary(null, Language.RU, "TARIFF_PER", "за"));
		list.add(new Dictionary(null, Language.EN, "TARIFF_PER", "per"));
		list.add(new Dictionary(null, Language.RU, "TARIFF_DAYS", "дней"));
		list.add(new Dictionary(null, Language.EN, "TARIFF_DAYS", "days"));
		list.add(new Dictionary(null, Language.RU, "TARIFF_COUNT_DAILY_REQUEST_TITLE", "Количество запросов доступное в день"));
		list.add(new Dictionary(null, Language.EN, "TARIFF_COUNT_DAILY_REQUEST_TITLE", "The number of requests available per day"));
		list.add(new Dictionary(null, Language.RU, "TARIFF_COUNT_MONTHLY_REQUEST_TITLE", "Количество запросов доступное на месяц"));
		list.add(new Dictionary(null, Language.EN, "TARIFF_COUNT_MONTHLY_REQUEST_TITLE", "The number of requests available per month"));
		list.add(new Dictionary(null, Language.RU, "TARIFF_DISCOUNT_TITLE", "Скидка"));
		list.add(new Dictionary(null, Language.EN, "TARIFF_DISCOUNT_TITLE", "Discount"));

		list.add(new Dictionary(null, Language.RU, "SESSION_HISTORY", "История посещений"));
		list.add(new Dictionary(null, Language.EN, "SESSION_HISTORY", "Session history"));
		list.add(new Dictionary(null, Language.RU, "SESSION_PAGING_ALL", "все"));
		list.add(new Dictionary(null, Language.EN, "SESSION_PAGING_ALL", "all"));
		list.add(new Dictionary(null, Language.RU, "SESSION_TABLE_COLUMN_BROWSE", "Браузер"));
		list.add(new Dictionary(null, Language.EN, "SESSION_TABLE_COLUMN_BROWSE", "Browse"));
		list.add(new Dictionary(null, Language.RU, "SESSION_TABLE_COLUMN_LAST_ACTIVE_DATE", "Дата последнего действия"));
		list.add(new Dictionary(null, Language.EN, "SESSION_TABLE_COLUMN_LAST_ACTIVE_DATE", "Most recent activity"));


//		Query query = em.createQuery("select d from Dictionary d where d.code = :code and d.language = :language");

//        ObjectMapper objectMapper=new ObjectMapper();
		for (final Dictionary item : list) {
//            System.out.println("awrankPost('rest/dictionary', "+ objectMapper.valueToTree(item).toString()+");");
			Dictionary oldItem = dictionaryDao.findByCodeAndLanguage(item.getCode(), item.getLanguage());
			if (oldItem != null) {
				if (!oldItem.getText().equals(item.getText())) {
					oldItem.setText(item.getText());
					dictionaryDao.save(oldItem);
				}
			} else {
//				item.setCreatedDate(DateTime.now());
//				item.setLastModifiedDate(item.getCreatedDate());
				dictionaryDao.save(item);
			}
		}
	}
}
