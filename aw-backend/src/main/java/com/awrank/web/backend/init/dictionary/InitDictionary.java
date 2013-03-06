package com.awrank.web.backend.init.dictionary;

import com.awrank.web.model.dao.DictionaryDao;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.enums.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Dictionary initialization
 *
 * @author Alex Polyakov
 * @author Andrew Stoyaltsev
 */
@Service
public class InitDictionary {

	@Autowired
	private DictionaryDao dictionaryDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void init() {
		final List<Dictionary> list = new ArrayList<Dictionary>();

		/* Errors */

		list.addAll(Dictionary.createItems(Message.ERROR.name(), "Error: ", "Ошибка: "));

		list.addAll(Dictionary.createItems(
				Message.ERROR_ACCESS.name(),
				"You're not permitted to do this action.",
				"У вас нет прав на выполнение операции."));

		list.addAll(Dictionary.createItems(
				Message.ERROR_NETWORK.name(),
				"No connection with the server, try later.",
				"Cоединение с сервером отсутствует, попробуйте позже."));

		list.addAll(Dictionary.createItems(
				Message.MISSING_OBJECT_ID.name(),
				"Missing object identifier.",
				"Отсутствует идентификатор объекта."));

		list.addAll(Dictionary.createItems(
				Message.OBJECT_NOT_UNIQUE.name(),
				"The object must be unique.",
				"Объект должен быть уникален."));

		list.addAll(Dictionary.createItems(
				Message.MISSING_DICTIONARY_LANGUAGE.name(),
				"Missing dictionary language.",
				"Словарь выбранного языка отсутствует."));

		list.addAll(Dictionary.createItems(
				Message.MISSING_DICTIONARY_CODE.name(),
				"Missing dictionary code",
				"Код словаря отсутствует."));

		list.addAll(Dictionary.createItems(
				Message.MISSING_DICTIONARY_TEXT.name(),
				"Missing dictionary text",
				"Отсутствует текст сообщения."));


		/* Common words and phrases */

		list.addAll(Dictionary.createItems("DICTIONARY", "Dictionary", "Словарь"));

		list.addAll(Dictionary.createItems("COUNTRY", "Country", "Страна"));
		list.addAll(Dictionary.createItems("IP_ADDRESS", "IP-address", "IP-адрес"));
		list.addAll(Dictionary.createItems("LANGUAGE", "Language", "Язык"));

		list.addAll(Dictionary.createItems("LOGIN", "Login", "Вход"));
		list.addAll(Dictionary.createItems("LOGOUT", "Logout", "Выход"));
		list.addAll(Dictionary.createItems("INSERT", "Insert", "Добавить"));
		list.addAll(Dictionary.createItems("DELETE", "Delete", "Удалить"));
		list.addAll(Dictionary.createItems("UPDATE", "Update", "Обновить"));

		list.addAll(Dictionary.createItems("PASSWORD", "Password", "Пароль"));
		list.addAll(Dictionary.createItems("PASSWORD_CONFIRM", "Password confirm", "Подтвердите пароль"));
		list.addAll(Dictionary.createItems("PAYMENT", "Payment", "Оплата"));
		list.addAll(Dictionary.createItems("SITE", "SITE", "САЙТ"));

		list.addAll(Dictionary.createItems("ORDER", "Order", "Заказ"));
		list.addAll(Dictionary.createItems("MORE", "more", "еще"));

		/* ENTITY */
		list.addAll(Dictionary.createItems("DICTIONARY_CODE", "Code", "Код"));
		list.addAll(Dictionary.createItems("DICTIONARY_TEXT", "Text", "Текст"));

		/* UI */
		list.addAll(Dictionary.createItems("MY_ACCOUNT", "My account", "Учетная запись"));
		list.addAll(Dictionary.createItems("FORGET_PASSWORD", "Forgotten your password?", "Забыли пароль?"));

		// Registration form
		list.addAll(Dictionary.createItems("CREATE_A_NEW_ACCOUNT", "Create a new account", "Создайте учетную запись"));
		list.addAll(Dictionary.createItems("WITH_SOCIAL_NETWORK", "with social network:", "с помощью социальной сети:"));
		list.addAll(Dictionary.createItems("SIGNUP_WITH_GOOGLE", "Sign up with Google", "Рег-я через Google"));
		list.addAll(Dictionary.createItems("SIGNUP_WITH_FACEBOOK", "Sign up with Facebook", "Рег-я через Facebook"));
		list.addAll(Dictionary.createItems("OR_WITH_EMAIL", "or with email:", "или введите email:"));

		list.addAll(Dictionary.createItems("REGISTRATION", "Registration", "Регистрация"));
		list.addAll(Dictionary.createItems("PER_MONTH", "per month", "в месяц"));

		list.addAll(Dictionary.createItems("LOGIN_WITH_GOOGLE", "Log in with Google", "Вход через Google"));
		list.addAll(Dictionary.createItems("LOGIN_WITH_FACEBOOK", "Log in with Facebook", "Вход через Facebook"));

		list.addAll(Dictionary.createItems("HOME", "Home", "Главная"));
		list.addAll(Dictionary.createItems("ACCOUNT", "ACCOUNT", "Учетная запись"));
		list.addAll(Dictionary.createItems("PROFILE", "Profile", "Профиль"));
		list.addAll(Dictionary.createItems("ACTIVITY", "ACTIVITY", "ДЕЯТЕЛЬНОСТЬ"));
		list.addAll(Dictionary.createItems("REQUEST_HISTORY", "Request history", "История запросов"));
		list.addAll(Dictionary.createItems("ADMINISTRATION", "Administration", "Администрирование"));

		list.addAll(Dictionary.createItems("EMAIL_ADDRESS", "Email address", "Email адрес"));

		list.addAll(Dictionary.createItems(
				"WE_RECOMMEND_SECRET_QUESTION",
				"We do recommend to set secret question and input answer to make account more secure:",
				"Мы рекомендуем установить секретный вопрос и ответ для большей безопасности:"));

		list.addAll(Dictionary.createItems(
				"SECRET_QUESTION_1",
				"What is your first pet's name?",
				"Кличка вашего первого домашнего животного?"));

		list.addAll(Dictionary.createItems(
				"SECRET_QUESTION_2",
				"What is your mother's maiden name?",
				"Девичья фамилия вашей матери?"));

		list.addAll(Dictionary.createItems(
				"SECRET_QUESTION_3",
				"Your favourite dish?",
				"Ваше любимое блюдо?"));

		list.addAll(Dictionary.createItems(
				"SECRET_QUESTION_4",
				"The most unforgettable event in your life?",
				"Самое незабываемое событие в вашей жизни?"));

		list.addAll(Dictionary.createItems(
				"INPUT_ANSWER_HERE", "Input answer here...",
				"Введите ответ здесь..."));

		list.addAll(Dictionary.createItems("I_AGREE_WITH_THE", "I agree with the", "Я согласен с"));
		list.addAll(Dictionary.createItems("TERMS_OF_SERVICE", "Terms of Service", "Условия использования"));
		list.addAll(Dictionary.createItems("DO_YOU_HAVE_AN_ACCOUNT", "Do you have an account?", "У вас есть учетная запись?"));

		// Login form
		list.addAll(Dictionary.createItems("LOGIN_TITLE", "Log in to your account", "Вход под своей учетной записью"));
		list.addAll(Dictionary.createItems("LOGIN_DESCRIPTION", "or with registered details:", "или введите свои данные:"));
		list.addAll(Dictionary.createItems("LOGIN_FIELD_LOGIN", "Email address or login", "Email адрес или логин"));
		list.addAll(Dictionary.createItems("LOGIN_FOOTER", "Don't have an account?", "У вас нет учетной записи?"));

		list.addAll(Dictionary.createItems(
				"FORGET_DESCRIPTION",
				"Enter your email address below and we'll send you password reset instructions.",
				"Введите адрес электронной почты ниже, и мы вышлем вам инструкцию востановления пароля."
		));
		list.addAll(Dictionary.createItems("FORGET_FIELD_EMAIL", "Email address", "Email адрес"));
		list.addAll(Dictionary.createItems("FORGET_BUTTON_SUBMIT", "Reset password", "Востановить пароль"));
		list.addAll(Dictionary.createItems("FORGET_FOOTER", "or return to", "или перейти на"));

		list.addAll(Dictionary.createItems("TARIFF_CHOOSE", "Choose plan", "Выбрать"));
		list.addAll(Dictionary.createItems("TARIFF_STARTED_NAME", "Started", "Стартовый"));
		list.addAll(Dictionary.createItems(
				"TARIFF_STARTED_DESCRIPTION",
				"Great for web master to get started",
				"Отлично подходит для веб-мастера, чтобы начать работу"));

		list.addAll(Dictionary.createItems("TARIFF_BASIC_NAME", "Basic", "Базовый"));

		list.addAll(Dictionary.createItems(
				"TARIFF_BASIC_DESCRIPTION",
				"Great for web master to get started2",
				"Отлично подходит для веб-мастера, чтобы начать работу2"));

		list.addAll(Dictionary.createItems("TARIFF_PRO_NAME", "Pro", "Профессиональный"));

		list.addAll(Dictionary.createItems(
				"TARIFF_PRO_DESCRIPTION",
				"Great for web master to get started",
				"Отлично подходит для веб-мастера, чтобы начать работу3"));

		list.addAll(Dictionary.createItems("TARIFF_PER", "per", "за"));
		list.addAll(Dictionary.createItems("TARIFF_DAYS", "days", "дней"));

		list.addAll(Dictionary.createItems(
				"TARIFF_COUNT_DAILY_REQUEST_TITLE",
				"The number of requests available per day",
				"Количество запросов доступное в день"));

		list.addAll(Dictionary.createItems(
				"TARIFF_COUNT_MONTHLY_REQUEST_TITLE",
				"The number of requests available per month",
				"Количество запросов доступное на месяц"));

		list.addAll(Dictionary.createItems("TARIFF_DISCOUNT_TITLE", "Discount", "Скидка"));

		list.addAll(Dictionary.createItems("SESSION_HISTORY", "Session history", "История посещений"));
		list.addAll(Dictionary.createItems("SESSION_PAGING_ALL", "all", "все"));
		list.addAll(Dictionary.createItems("SESSION_TABLE_COLUMN_BROWSE", "Browse", "Браузер")); // обзор?

		list.addAll(Dictionary.createItems(
				"SESSION_TABLE_COLUMN_LAST_ACTIVE_DATE",
				"Most recent activity",
				"Дата последнего действия"));


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
