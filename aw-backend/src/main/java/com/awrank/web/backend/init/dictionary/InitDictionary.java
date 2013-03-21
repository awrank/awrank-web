package com.awrank.web.backend.init.dictionary;

import com.awrank.web.model.dao.DictionaryDao;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.Language;
import com.awrank.web.model.domain.PaymentSystemType;
import com.awrank.web.model.enums.Currency;
import com.awrank.web.model.enums.Message;
import com.awrank.web.model.service.impl.pojos.UserNewPasswordFormPojo;

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
 * @author Olga Korokhina
 */
@Service
public class InitDictionary {

	@Autowired
	private DictionaryDao dictionaryDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void init() {
		final List<Dictionary> list = new ArrayList<Dictionary>();
//        ERROR
		list.add(new Dictionary(null, Language.RU, Message.ERROR.name(), "Ошибка: "));
		list.add(new Dictionary(null, Language.EN, Message.ERROR.name(), "Error: "));
		list.add(new Dictionary(null, Language.RU, Message.ERROR_ACCESS.name(), "Недостаточно прав для выполнения этой операции."));
		list.add(new Dictionary(null, Language.EN, Message.ERROR_ACCESS.name(), "You're not permitted to do this action."));
		list.add(new Dictionary(null, Language.RU, Message.ERROR_NETWORK.name(), "Соединение с сервером отсутствует, попробуйте позже."));
		list.add(new Dictionary(null, Language.EN, Message.ERROR_NETWORK.name(), "No connection with the server, try later."));

		list.add(new Dictionary(null, Language.RU, Message.YOU_HAVE_TO_VERIFY_YOUR_CURRENT_EMAIL_FIRST.name(), "Вы не можете сменить email до тех пор пока не верифицируете текущий"));
		list.add(new Dictionary(null, Language.EN, Message.YOU_HAVE_TO_VERIFY_YOUR_CURRENT_EMAIL_FIRST.name(), "You cannot change your email before verify current one."));
		list.add(new Dictionary(null, Language.RU, Message.YOU_HAVE_TO_VERIFY_YOUR_CURRENT_EMAIL_FIRST_PASSWORD.name(), "Вы не можете сменить пароль до тех пор пока не верифицируете текущий почтовый ящик"));
		list.add(new Dictionary(null, Language.EN, Message.YOU_HAVE_TO_VERIFY_YOUR_CURRENT_EMAIL_FIRST_PASSWORD.name(), "You cannot change your password before verify current email."));
		
		list.add(new Dictionary(null, Language.RU, "AUTH_ERROR", "Ошибка аутентификации"));
		list.add(new Dictionary(null, Language.EN, "AUTH_ERROR", "Authentication error"));
		list.add(new Dictionary(null, Language.RU, "MESSAGE", "Сообщение: "));
		list.add(new Dictionary(null, Language.EN, "MESSAGE", "Message: "));
		list.add(new Dictionary(null, Language.RU, "DESCRIPTION", "Описание: "));
		list.add(new Dictionary(null, Language.EN, "DESCRIPTION", "Description: "));
		list.add(new Dictionary(null, Language.RU, "ERROR_CODE", "Код ошибки: "));
		list.add(new Dictionary(null, Language.EN, "ERROR_CODE", "Error code: "));

//
		list.add(new Dictionary(null, Language.RU, Message.MISSING_OBJECT_ID.name(), "Отсутствует идентификатор объекта."));
		list.add(new Dictionary(null, Language.EN, Message.MISSING_OBJECT_ID.name(), "Missing object identifier."));
		list.add(new Dictionary(null, Language.RU, Message.OBJECT_NOT_UNIQUE.name(), "Объект должен быть уникален."));
		list.add(new Dictionary(null, Language.EN, Message.OBJECT_NOT_UNIQUE.name(), "The object must be unique."));

		list.add(new Dictionary(null, Language.RU, Message.MISSING_DICTIONARY_LANGUAGE.name(), "Отсутствует язык."));
		list.add(new Dictionary(null, Language.EN, Message.MISSING_DICTIONARY_LANGUAGE.name(), "Missing language."));
		list.add(new Dictionary(null, Language.RU, Message.MISSING_DICTIONARY_CODE.name(), "Отсутствует код."));
		list.add(new Dictionary(null, Language.EN, Message.MISSING_DICTIONARY_CODE.name(), "Missing dictionary code."));
		list.add(new Dictionary(null, Language.RU, Message.MISSING_DICTIONARY_TEXT.name(), "Отсутствует текст сообщения."));
		list.add(new Dictionary(null, Language.EN, Message.MISSING_DICTIONARY_TEXT.name(), "Missing dictionary text."));

		list.add(new Dictionary(null, Language.RU, Message.ENTRY_POINT_BY_EMAIL_NOT_FOUND.name(), "Указанный адрес электронной почты отсутствует."));
		list.add(new Dictionary(null, Language.EN, Message.ENTRY_POINT_BY_EMAIL_NOT_FOUND.name(), "The email not found."));
		list.add(new Dictionary(null, Language.RU, Message.ENTRY_POINT_WRONG_CURRENT_PASSWORD.name(), "Неправильный текущий пароль."));
		list.add(new Dictionary(null, Language.EN, Message.ENTRY_POINT_WRONG_CURRENT_PASSWORD.name(), "Wrong current password."));

		list.add(new Dictionary(null, Language.RU, Message.USER_EMAIL_ACTIVATION_NOT_SENT.name(), "Ссылка для активации почтового ящика НЕ отправлена!"));
		list.add(new Dictionary(null, Language.EN, Message.USER_EMAIL_ACTIVATION_NOT_SENT.name(), "Email activation link NOT sent!"));
		
		// social
		list.add(new Dictionary(null, Language.RU,
				Message.SOCIAL_NETWORK_EMAIL_NOT_PROVIDED.name(),
				"К сожалению, социальная сеть, которую вы выбрали не может предоставить нам ваш email. " +
						"Пожалуйста, укажите ваш email в профиле социальной сети и попробуйте снова. " +
						"Либо вы можете использовать стандартную форму регистрации. " +
						"Спасибо за понимание."));
		list.add(new Dictionary(null, Language.EN,
				Message.SOCIAL_NETWORK_EMAIL_NOT_PROVIDED.name(),
				"Unfortunately, the social network you've chosen did not provide us " +
						"with your email. Please set email in your social account and try again or " +
						"use the standard registration form. Thanks for understanding."));

		list.add(new Dictionary(null, Language.RU,
				Message.SOCIAL_NEGATIVE_RESPONSE_RECEIVED.name(),
				"К сожалению, социальная сеть, вернула отрицательный результат."));
		list.add(new Dictionary(null, Language.EN,
				Message.SOCIAL_NEGATIVE_RESPONSE_RECEIVED.name(),
				"Unfortunately, the social network has returned a negative response."));

		list.add(new Dictionary(null, Language.RU,
				Message.SOCIAL_INVALID_GOOGLE_STATE_PARAM_VALUE.name(),
				"Значения параметра 'state' в ответе не соотвествует значению в запросе!"));
		list.add(new Dictionary(null, Language.EN,
				Message.SOCIAL_INVALID_GOOGLE_STATE_PARAM_VALUE.name(),
				"Custom 'state' param value does not correspond initial request value!"));

		list.add(new Dictionary(null, Language.RU,
				Message.SOCIAL_MISSING_GOOGLE_STATE_PARAM.name(),
				"Параметр 'state' не определен!"));
		list.add(new Dictionary(null, Language.EN,
				Message.SOCIAL_MISSING_GOOGLE_STATE_PARAM.name(),
				"Parameter 'state' is not specified!"));

		list.add(new Dictionary(null, Language.RU,
				Message.SOCIAL_REQUEST_ACCESS_TOKEN_FAILED.name(), "Ошибка получения кода доступа (access_token)!"));
		list.add(new Dictionary(null, Language.EN,
				Message.SOCIAL_REQUEST_ACCESS_TOKEN_FAILED.name(), "Request for getting access_token value has failed!"));

		list.add(new Dictionary(null, Language.RU,
				Message.SOCIAL_REQUEST_USER_PROFILE_FAILED.name(), "Ошибка получения данных о профиле пользователя (user_profile) !"));
		list.add(new Dictionary(null, Language.EN,
				Message.SOCIAL_REQUEST_USER_PROFILE_FAILED.name(), "Request for getting user_profile has failed!"));

		list.add(new Dictionary(null, Language.RU, Message.SOCIAL_NO_AUTH_ACTION_SPECIFIED.name(), "Не задан тип действия для аутентификации пользователя."));
		list.add(new Dictionary(null, Language.EN, Message.SOCIAL_NO_AUTH_ACTION_SPECIFIED.name(), "No auth action specified!"));

		list.add(new Dictionary(null, Language.RU, Message.SOCIAL_ACCESS_TOKEN_IS_NULL.name(), "Ключ доступа (access_token) равен NULL."));
		list.add(new Dictionary(null, Language.EN, Message.SOCIAL_ACCESS_TOKEN_IS_NULL.name(), "Access token is null."));

		list.add(new Dictionary(null, Language.RU, Message.SOCIAL_AUTH_CODE_IS_NULL.name(), "Код аутентификации (auth_code) равен NULL."));
		list.add(new Dictionary(null, Language.EN, Message.SOCIAL_AUTH_CODE_IS_NULL.name(), "Auth code is null."));

		list.add(new Dictionary(null, Language.RU, Message.SOCIAL_LOGIN_FAILED_TITLE.name(),
				"К сожалению, возникли ошибки при выполнении операции входа в систему."));
		list.add(new Dictionary(null, Language.EN, Message.SOCIAL_LOGIN_FAILED_TITLE.name(),
				"Unfortunately, errors occurred during login action to the system."));

		list.add(new Dictionary(null, Language.RU, Message.SOCIAL_REGISTER_FAILED_TITLE.name(),
				"К сожалению, возникли ошибки при выполнении операции регистрации."));
		list.add(new Dictionary(null, Language.EN, Message.SOCIAL_REGISTER_FAILED_TITLE.name(),
				"Unfortunately, errors occurred during registration process."));


		list.add(new Dictionary(null, Language.RU,
				Message.ENTRY_POINT_NOT_FOUND_BY_UID.name(), "К сожалению, мы не смогли найти запись entry-point."));
		list.add(new Dictionary(null, Language.EN,
				Message.ENTRY_POINT_NOT_FOUND_BY_UID.name(), "Unfortunately, we could not find entry-point record."));


		list.add(new Dictionary(null, Language.RU,
				Message.EMAIL_ALREADY_EXISTS.name(), "Указанный email уже зарегистрирован в системе."));
		list.add(new Dictionary(null, Language.EN,
				Message.EMAIL_ALREADY_EXISTS.name(), "This email is already registered in the system!"));


//        simple words
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
		list.add(new Dictionary(null, Language.EN, "PASSWORD_CONFIRM", "Confirm password"));
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
		list.add(new Dictionary(null, Language.RU, "WARNING", "Предупреждение"));
		list.add(new Dictionary(null, Language.EN, "WARNING", "Warning"));
		list.add(new Dictionary(null, Language.RU, "CONTINUE", "Продолжить"));
		list.add(new Dictionary(null, Language.EN, "CONTINUE", "Continue"));
		list.add(new Dictionary(null, Language.RU, "UNDER_CONSTRUCTION", "В разработке."));
		list.add(new Dictionary(null, Language.EN, "UNDER_CONSTRUCTION", "Under construction."));
		list.add(new Dictionary(null, Language.RU, "CREATED_AT", "Создано"));
		list.add(new Dictionary(null, Language.EN, "CREATED_AT", "Created at"));
		list.add(new Dictionary(null, Language.RU, "ENDED_AT", "Ликвидировано"));
		list.add(new Dictionary(null, Language.EN, "ENDED_AT", "Ended at"));
		list.add(new Dictionary(null, Language.RU, "SEARCH", "Искать"));
		list.add(new Dictionary(null, Language.RU, "SEARCH", "Search"));
		list.add(new Dictionary(null, Language.RU, "ALL", "Все"));
		list.add(new Dictionary(null, Language.EN, "ALL", "All"));
		list.add(new Dictionary(null, Language.RU, "WELCOME", "Добро пожаловать!"));
		list.add(new Dictionary(null, Language.EN, "WELCOME", "Welcome!"));
		list.add(new Dictionary(null, Language.RU, "HELLO_USERNAME", "Привет, "));
		list.add(new Dictionary(null, Language.EN, "HELLO_USERNAME", "Hello, "));
		list.add(new Dictionary(null, Language.RU, "USER", "Пользователь"));
		list.add(new Dictionary(null, Language.EN, "USER", "User"));
		list.add(new Dictionary(null, Language.RU, "ENTRY_POINT", "Точка входа"));
		list.add(new Dictionary(null, Language.EN, "ENTRY_POINT", "Entry point"));
		list.add(new Dictionary(null, Language.RU, "SESSION_ID", "ID Сессии"));
		list.add(new Dictionary(null, Language.EN, "SESSION_ID", "Session ID"));
		list.add(new Dictionary(null, Language.RU, "SUCCESS", "Успешно"));
		list.add(new Dictionary(null, Language.EN, "SUCCESS", "Success"));
		list.add(new Dictionary(null, Language.RU, "SPENT_REQUESTS", "Запросов"));
		list.add(new Dictionary(null, Language.EN, "SPENT_REQUESTS", "Requests"));

		// CURRENCY
		list.add(new Dictionary(null, Language.RU, Currency.USD.name(), "Доллар"));
		list.add(new Dictionary(null, Language.EN, Currency.USD.name(), "Dollar"));
		list.add(new Dictionary(null, Language.RU, Currency.EUR.name(), "Евро"));
		list.add(new Dictionary(null, Language.EN, Currency.EUR.name(), "Euro"));
		list.add(new Dictionary(null, Language.RU, Currency.RUB.name(), "Рубль"));
		list.add(new Dictionary(null, Language.EN, Currency.RUB.name(), "Ruble"));
		list.add(new Dictionary(null, Language.RU, Currency.UAH.name(), "Гривна"));
		list.add(new Dictionary(null, Language.EN, Currency.UAH.name(), "Hryvnia"));
		// PAYMENT SYSTEM
		list.add(new Dictionary(null, Language.RU, PaymentSystemType.TWO_CHECKOUT.name(), "2CheckOut"/*"Тучекаут"*/));
		list.add(new Dictionary(null, Language.EN, PaymentSystemType.TWO_CHECKOUT.name(), "2CheckOut"));
		list.add(new Dictionary(null, Language.RU, PaymentSystemType.WEB_MONEY.name(), "WebMoney"/*"Вебмани"*/));
		list.add(new Dictionary(null, Language.EN, PaymentSystemType.WEB_MONEY.name(), "WebMoney"));

//      ENTITY
		list.add(new Dictionary(null, Language.RU, "DICTIONARY_CODE", "Код"));
		list.add(new Dictionary(null, Language.EN, "DICTIONARY_CODE", "Code"));
		list.add(new Dictionary(null, Language.RU, "DICTIONARY_TEXT", "Текст"));
		list.add(new Dictionary(null, Language.EN, "DICTIONARY_TEXT", "Text"));

//      UI
		list.add(new Dictionary(null, Language.RU, "MY_ACCOUNT", " Учетная запись"));
		list.add(new Dictionary(null, Language.EN, "MY_ACCOUNT", "My account"));

		list.add(new Dictionary(null, Language.RU, "FORGOT_PASSWORD", "Забыли пароль?"));
		list.add(new Dictionary(null, Language.EN, "FORGOT_PASSWORD", "Forgot your password?"));

		list.add(new Dictionary(null, Language.RU, "REGISTRATION", "Регистрация"));
		list.add(new Dictionary(null, Language.EN, "REGISTRATION", "Registration"));
		list.add(new Dictionary(null, Language.RU, "PER_MONTH", "в месяц"));
		list.add(new Dictionary(null, Language.EN, "PER_MONTH", "per month"));
		list.add(new Dictionary(null, Language.RU, "WITH_SOCIAL_NETWORK", "с помощью социальных сетей:"));
		list.add(new Dictionary(null, Language.EN, "WITH_SOCIAL_NETWORK", "with social network:"));
		list.add(new Dictionary(null, Language.RU, "LOGIN_WITH_GOOGLE", "Вход через Google"));
		list.add(new Dictionary(null, Language.EN, "LOGIN_WITH_GOOGLE", "Log in with Google"));
		list.add(new Dictionary(null, Language.RU, "LOGIN_WITH_FACEBOOK", "Вход через Facebook"));
		list.add(new Dictionary(null, Language.EN, "LOGIN_WITH_FACEBOOK", "Log in with Facebook"));

		list.add(new Dictionary(null, Language.RU, "HOME", "Главная"));
		list.add(new Dictionary(null, Language.EN, "HOME", "Home"));
		list.add(new Dictionary(null, Language.RU, "ACCOUNT", "Учетная запись"));
		list.add(new Dictionary(null, Language.EN, "ACCOUNT", "ACCOUNT"));
		list.add(new Dictionary(null, Language.RU, "PROFILE", "Профиль"));
		list.add(new Dictionary(null, Language.EN, "PROFILE", "Profile"));
		list.add(new Dictionary(null, Language.RU, "APPLICATION", "Приложение"));
		list.add(new Dictionary(null, Language.EN, "APPLICATION", "Application"));
		list.add(new Dictionary(null, Language.RU, "ACTIVITY", "ДЕЯТЕЛЬНОСТЬ"));
		list.add(new Dictionary(null, Language.EN, "ACTIVITY", "ACTIVITY"));
		list.add(new Dictionary(null, Language.RU, "REQUEST_HISTORY", "История запросов"));
		list.add(new Dictionary(null, Language.EN, "REQUEST_HISTORY", "Request history"));
		list.add(new Dictionary(null, Language.RU, "ADMINISTRATION", "Администрирование"));
		list.add(new Dictionary(null, Language.EN, "ADMINISTRATION", "Administration"));
		list.add(new Dictionary(null, Language.RU, "NAME", "ФИО"));
		list.add(new Dictionary(null, Language.EN, "NAME", "Name"));
		list.add(new Dictionary(null, Language.RU, "EMAIL", "Email"));
		list.add(new Dictionary(null, Language.EN, "EMAIL", "Email"));
		list.add(new Dictionary(null, Language.RU, "API_KEY", "Ключ API"));
		list.add(new Dictionary(null, Language.EN, "API_KEY", "API key"));
		list.add(new Dictionary(null, Language.RU, "SKYPE", "Skype"));
		list.add(new Dictionary(null, Language.EN, "SKYPE", "Skype"));


		list.add(new Dictionary(null, Language.RU, "PROFILE_FIELD_BIRTHDAY", "Дата рождения (ГГГГ-ММ-ДД"));
		list.add(new Dictionary(null, Language.EN, "PROFILE_FIELD_BIRTHDAY", "Birthday (YYYY-MM-DD)"));
		list.add(new Dictionary(null, Language.RU, "PROFILE_FIELD_CURRENT_PASSWORD", "Текущий пароль"));
		list.add(new Dictionary(null, Language.EN, "PROFILE_FIELD_CURRENT_PASSWORD", "Current Password"));
		list.add(new Dictionary(null, Language.RU, "PROFILE_FIELD_NEW_PASSWORD", "Новый пароль"));
		list.add(new Dictionary(null, Language.EN, "PROFILE_FIELD_NEW_PASSWORD", "New Password"));
		list.add(new Dictionary(null, Language.RU, "PROFILE_FIELD_NEW_PASSWORD_CONFIRM", "Новый пароль ещё раз"));
		list.add(new Dictionary(null, Language.EN, "PROFILE_FIELD_NEW_PASSWORD_CONFIRM", "New Password Confirmation"));

		list.add(new Dictionary(null, Language.RU, "REGISTER_DESCRIPTION", "или введите email:"));
		list.add(new Dictionary(null, Language.EN, "REGISTER_DESCRIPTION", "or with email:"));
		list.add(new Dictionary(null, Language.RU, "FIRST_NAME", "Имя"));
		list.add(new Dictionary(null, Language.EN, "FIRST_NAME", "First Name"));
		list.add(new Dictionary(null, Language.RU, "LAST_NAME", "Фамилия"));
		list.add(new Dictionary(null, Language.EN, "LAST_NAME", "Last Name"));
		list.add(new Dictionary(null, Language.RU, "REGISTER_FIELD_LANGUAGE", "Язык (RU, EN)"));
		list.add(new Dictionary(null, Language.EN, "REGISTER_FIELD_LANGUAGE", "Language (RU, EN)"));
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
		list.add(new Dictionary(null, Language.RU, Message.REGISTRATION_EMAIL_MALFORMED.name(), "Неверный формат электронной почты"));
		list.add(new Dictionary(null, Language.EN, Message.REGISTRATION_EMAIL_MALFORMED.name(), "Wrong email format"));
		list.add(new Dictionary(null, Language.RU, Message.REGISTRATION_YOU_HAVE_TO_AGREE_WITH_TAC.name(), "Вы должны согласиться с Условиями Пользования"));
		list.add(new Dictionary(null, Language.EN, Message.REGISTRATION_YOU_HAVE_TO_AGREE_WITH_TAC.name(), "You have to agree with Terms and Conditions"));
		list.add(new Dictionary(null, Language.RU, Message.REGISTRATION_PASSWORD_AND_CONFIRMATION_ARE_NOT_EQUAL.name(), "Пароль и его подтверджение не идентичны!"));
		list.add(new Dictionary(null, Language.EN, Message.REGISTRATION_PASSWORD_AND_CONFIRMATION_ARE_NOT_EQUAL.name(), "Password and confirmation are not equal!"));
		list.add(new Dictionary(null, Language.RU, Message.YOU_REGISTERED_SUCCESSFULLY_VERIFICATION_EMAIL_SENT.name(), "Регистрация прошла успешно, на указанный почтовый ящик выслана ссылка для верификации- пожалуйста, верифицируйтесь!"));
		list.add(new Dictionary(null, Language.EN, Message.YOU_REGISTERED_SUCCESSFULLY_VERIFICATION_EMAIL_SENT.name(), "You've registered successfully, verification link is sent on the mailbox you specified- please verify!"));
		list.add(new Dictionary(null, Language.RU, Message.YOU_REGISTERED_SUCCESSFULLY.name(), "Регистрация прошла успешно."));
		list.add(new Dictionary(null, Language.EN, Message.YOU_REGISTERED_SUCCESSFULLY.name(), "You've registered successfully."));

		list.add(new Dictionary(null, Language.RU, Message.EMAIL_VERIFIED_SUCCESSFULLY.name(), "Почтовый ящик успешно верифицирован."));
		list.add(new Dictionary(null, Language.EN, Message.EMAIL_VERIFIED_SUCCESSFULLY.name(), "Email box verified successfully."));
		list.add(new Dictionary(null, Language.RU, Message.EMAIL_NOT_VERIFIED.name(), "Почтовый ящик НЕ верифицирован!"));
		list.add(new Dictionary(null, Language.EN, Message.EMAIL_NOT_VERIFIED.name(), "Email box NOT verified!"));

		list.add(new Dictionary(null, Language.RU, Message.PROFILE_FIRST_LAST_NAMES_TOO_SHORT.name(), "Имя и фамилия должны быть длинее"));
		list.add(new Dictionary(null, Language.EN, Message.PROFILE_FIRST_LAST_NAMES_TOO_SHORT.name(), "First and Last names shall be longer"));
		list.add(new Dictionary(null, Language.RU, Message.PROFILE_UPDATED_SUCCESSFULLY.name(), "Ваши данные обновлены успешно!"));
		list.add(new Dictionary(null, Language.EN, Message.PROFILE_UPDATED_SUCCESSFULLY.name(), "Your profile data was updated successfully!"));
		list.add(new Dictionary(null, Language.RU, Message.PROFILE_EMAIL_UPDATED_SUCCESSFULLY.name(), "Почтовый ящик изменён. На новый ящик выслан линк для верификации, до момента верификации нового ящика будет использоваться текущий."));
		list.add(new Dictionary(null, Language.EN, Message.PROFILE_EMAIL_UPDATED_SUCCESSFULLY.name(), "Your mail box data chaged successfully. Verification link sent to new email- untill you will verify it current email is used."));
		list.add(new Dictionary(null, Language.RU, Message.PROFILE_PASSWORD_UPDATED_SUCCESSFULLY.name(), "Пароль обновлён. На Ваш почтовый ящик выслан линк для верификации, до момента верификации нового пароля будет использоваться текущий."));
		list.add(new Dictionary(null, Language.EN, Message.PROFILE_PASSWORD_UPDATED_SUCCESSFULLY.name(), "Your password updated successfully. Verification link sent to you email box- untill you will verify it current password is used."));
		list.add(new Dictionary(null, Language.RU, Message.PASSWORD_VERIFIED_SUCCESSFULLY.name(), "Новый пароль успешно подтверждён, старый более недействителен."));
		list.add(new Dictionary(null, Language.EN, Message.PASSWORD_VERIFIED_SUCCESSFULLY.name(), "New password updated successfully. Old one is no longer valid"));
		list.add(new Dictionary(null, Language.RU, Message.ENTERED_WRONG_CURRENT_PASSWORD.name(), "Вы ввели неверный текущий пароль"));
		list.add(new Dictionary(null, Language.EN, Message.ENTERED_WRONG_CURRENT_PASSWORD.name(), "You ebtered wrong current password"));
		list.add(new Dictionary(null, Language.RU, Message.FORGOT_PASSWORD_LINK_SENT.name(), "Ссылка для смены пароля отправлена на указанный почтовый ящик"));
		list.add(new Dictionary(null, Language.EN, Message.FORGOT_PASSWORD_LINK_SENT.name(), "Link for password reset sent to specified email"));
		list.add(new Dictionary(null, Language.RU, Message.FORGOT_PASSWORD_UID_NOT_FOUND.name(), "Указанный постовый ящик не зарегистрирован в системе"));
		list.add(new Dictionary(null, Language.EN, Message.FORGOT_PASSWORD_UID_NOT_FOUND.name(), "Email you set not registered in system"));
		list.add(new Dictionary(null, Language.RU, Message.FORGOT_PASSWORD_ATTEMPT_TO_CHANGE_WRONG_EMAIL.name(), "Вы залогинены сейчас не с тем почтовым ящиком который Вы ввели и у Вас недостаточно прав на смену чужих ящиков!"));
		list.add(new Dictionary(null, Language.EN, Message.FORGOT_PASSWORD_ATTEMPT_TO_CHANGE_WRONG_EMAIL.name(), "You're now logged in with different mailbox and you don't have enough rights to change someone else's box"));
		list.add(new Dictionary(null, Language.RU, Message.FORGOT_PASSWORD_NO_VALIDATED_EMAIL.name(), "Ваш почтовый ящик не верифицирован, вы не можете сбросить пароль - свяжитесь с службой поддержки!"));
		list.add(new Dictionary(null, Language.EN, Message.FORGOT_PASSWORD_NO_VALIDATED_EMAIL.name(), "Your mailbox is not verified, you can't reset password - please contact support!"));
		list.add(new Dictionary(null, Language.RU, Message.FORGOT_PASSWORD_BLOCKED_USER.name(), "Ваш аккаунт заблокирован - свяжитесь с службой поддержки!"));
		list.add(new Dictionary(null, Language.EN, Message.FORGOT_PASSWORD_BLOCKED_USER.name(), "Your account is blocked - please contact support!"));
		list.add(new Dictionary(null, Language.RU, Message.FORGOT_PASSWORD_LINK_NOT_VERIFIED.name(), "Ссылка для восстановления пароля не верифицированна, свяжитесь со службой поддержки!"));
		list.add(new Dictionary(null, Language.EN, Message.FORGOT_PASSWORD_LINK_NOT_VERIFIED.name(), "Password restore link is not verified, please contact the support!"));
		list.add(new Dictionary(null, Language.RU, Message.FORGOT_PASSWORD_VERIFIED_SUCCESSFULLY.name(), "Ссылка для восстановления пароля проверенна, введите новый пароль в форме ниже"));
		list.add(new Dictionary(null, Language.EN, Message.FORGOT_PASSWORD_VERIFIED_SUCCESSFULLY.name(), "Password restore link verified successfully, please enter new password in a form below"));
		list.add(new Dictionary(null, Language.RU, "FORGOT_FORM_INSTRUCTIONS", "Введите на этой форме новый пароль и его подтверждение. Вам на почту будет отправлен ещё один линк - только после того как вы перейдёте по нему новый пароль вступит в действие. Такая двуступенчатая проверка повышает безопастность, просим извинить если это неудобно Вам!"));
		list.add(new Dictionary(null, Language.EN, "FORGOT_FORM_INSTRUCTIONS", "In this form please enter new password. You will get one more link on your email - only after you will use this link new password can be used. This two-step verification done for security reasons, sorry for inconvinience!"));
		
		
		list.add(new Dictionary(null, Language.RU, "LOGIN_TITLE", "Вход под своей учетной записью"));
		list.add(new Dictionary(null, Language.EN, "LOGIN_TITLE", "Log in to your account"));
		list.add(new Dictionary(null, Language.RU, "LOGIN_DESCRIPTION", "или введите свои данные:"));
		list.add(new Dictionary(null, Language.EN, "LOGIN_DESCRIPTION", "or with registered details:"));
		list.add(new Dictionary(null, Language.RU, "LOGIN_FIELD_LOGIN", "Email адрес или логин"));
		list.add(new Dictionary(null, Language.EN, "LOGIN_FIELD_LOGIN", "Email address or login"));
		list.add(new Dictionary(null, Language.RU, "LOGIN_FOOTER", "У вас нет учетной записи?"));
		list.add(new Dictionary(null, Language.EN, "LOGIN_FOOTER", "Don't have an account?"));
		list.add(new Dictionary(null, Language.EN, "LOGIN_WRONG_UID_OR_PASSWORD", "Wrong login or email or password"));
		list.add(new Dictionary(null, Language.RU, "LOGIN_WRONG_UID_OR_PASSWORD", "Неправильный адресс электроной почты или логин или пароль"));
		list.add(new Dictionary(null, Language.RU, Message.YOU_LOGGED_IN_SUCCESSFULLY.name(), "Вы успешно вошли в систему."));
		list.add(new Dictionary(null, Language.EN, Message.YOU_LOGGED_IN_SUCCESSFULLY.name(), "You've logged in successfully."));

		list.add(new Dictionary(null, Language.RU, "FORGET_DESCRIPTION", "Введите адрес электронной почты ниже, и мы вышлем вам инструкцию восстановления пароля."));
		list.add(new Dictionary(null, Language.EN, "FORGET_DESCRIPTION", "Enter your email address below and we'll send you password reset instructions."));
		list.add(new Dictionary(null, Language.RU, "FORGET_FIELD_EMAIL", "Email адрес"));
		list.add(new Dictionary(null, Language.EN, "FORGET_FIELD_EMAIL", "Email address"));
		list.add(new Dictionary(null, Language.RU, "FORGET_BUTTON_SUBMIT", "Восстановить пароль"));
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
		list.add(new Dictionary(null, Language.RU, "SESSION_TABLE_COLUMN_SINGIN_DATE", "Дата входа"));
		list.add(new Dictionary(null, Language.EN, "SESSION_TABLE_COLUMN_SINGIN_DATE", "Singin date"));
		list.add(new Dictionary(null, Language.RU, "SESSION_TABLE_COLUMN_SINGOUT_DATE", "Дата последнего действия"));
		list.add(new Dictionary(null, Language.EN, "SESSION_TABLE_COLUMN_SINGOUT_DATE", "Most recent activity"));

		/* Administration */
		list.add(new Dictionary(null, Language.RU, "USER_LIST", "Список пользователей"));
		list.add(new Dictionary(null, Language.EN, "USER_LIST", "User list"));
		list.add(new Dictionary(null, Language.RU, "MANAGE_USERS", "Управление пользователями"));
		list.add(new Dictionary(null, Language.EN, "MANAGE_USERS", "Manage users"));
		list.add(new Dictionary(null, Language.RU, "LOGS", "Логи"));
		list.add(new Dictionary(null, Language.EN, "LOGS", "Logs"));

		list.add(new Dictionary(null, Language.RU, "PAYMENT_HISTORY", "История заказов"));
		list.add(new Dictionary(null, Language.EN, "PAYMENT_HISTORY", "Payment history"));
		list.add(new Dictionary(null, Language.RU, "PAYMENT_HISTORY_TABLE_COLUMN_TARIFF", "Тариф"));
		list.add(new Dictionary(null, Language.EN, "PAYMENT_HISTORY_TABLE_COLUMN_TARIFF", "Tariff"));
		list.add(new Dictionary(null, Language.RU, "PAYMENT_HISTORY_TABLE_COLUMN_PRICE", "Цена"));
		list.add(new Dictionary(null, Language.EN, "PAYMENT_HISTORY_TABLE_COLUMN_PRICE", "Price"));
		list.add(new Dictionary(null, Language.RU, "PAYMENT_HISTORY_TABLE_COLUMN_PAYMENT_SYSTEM", "Платежная система"));
		list.add(new Dictionary(null, Language.EN, "PAYMENT_HISTORY_TABLE_COLUMN_PAYMENT_SYSTEM", "Payment system"));
		list.add(new Dictionary(null, Language.RU, "PAYMENT_HISTORY_TABLE_COLUMN_DATE_BEGIN", "С"));
		list.add(new Dictionary(null, Language.EN, "PAYMENT_HISTORY_TABLE_COLUMN_DATE_BEGIN", "Begin"));
		list.add(new Dictionary(null, Language.RU, "PAYMENT_HISTORY_TABLE_COLUMN_DATE_END", "По"));
		list.add(new Dictionary(null, Language.EN, "PAYMENT_HISTORY_TABLE_COLUMN_DATE_END", "End"));

		// TODO check
		list.addAll(Dictionary.createItems("I_AGREE_WITH_THE", "I agree with the", "Я согласен с"));
		list.addAll(Dictionary.createItems("CREATE_A_NEW_ACCOUNT", "Create a new account", "Создайте учетную запись"));
		list.addAll(Dictionary.createItems("DO_YOU_HAVE_AN_ACCOUNT", "Do you have an account?", "У вас есть учетная запись?"));
		list.addAll(Dictionary.createItems("EMAIL_ADDRESS", "Email address", "Email адрес"));
		list.addAll(Dictionary.createItems("INPUT_ANSWER_HERE", "Input answer here...", "Введите ответ здесь..."));
		list.addAll(Dictionary.createItems("OR_WITH_EMAIL", "or with email:", "или введите email:"));
		list.addAll(Dictionary.createItems("SECRET_QUESTION_FIRST_PET", "What is your first pet's name?", "Кличка вашего первого домашнего животного?"));
		list.addAll(Dictionary.createItems("SECRET_QUESTION_MOTHERS_MAIDEN_NAME", "What is your mother's maiden name?", "Девичья фамилия вашей матери?"));
		list.addAll(Dictionary.createItems("SECRET_QUESTION_FAVOURITE_DISH", "Your favourite dish?", "Ваше любимое блюдо?"));
		list.addAll(Dictionary.createItems("SECRET_QUESTION_UNFORGETTABLE_EVENT", "The most unforgettable event in your life?", "Самое незабываемое событие в вашей жизни?"));
		list.addAll(Dictionary.createItems("SIGNUP_WITH_GOOGLE", "Sign up with Google", "Рег-я через Google"));
		list.addAll(Dictionary.createItems("SIGNUP_WITH_FACEBOOK", "Sign up with Facebook", "Рег-я через Facebook"));
		list.addAll(Dictionary.createItems("TERMS_OF_SERVICE", "Terms of Service", "Условия использования"));
		list.addAll(Dictionary.createItems("WE_RECOMMEND_SECRET_QUESTION", "We do recommend to set secret question and input answer to make account more secure:", "Мы рекомендуем установить секретный вопрос и ответ для большей безопасности:"));


		for (final Dictionary item : list) {
			Dictionary oldItem = dictionaryDao.findOneByLanguageAndCode(item.getLanguage(), item.getCode());
			if (oldItem != null) {
				if (!oldItem.getText().equals(item.getText())) {
					oldItem.setText(item.getText());
					dictionaryDao.save(oldItem);
				}
			} else {
				dictionaryDao.save(item);
			}
		}
	}
}
