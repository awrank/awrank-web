package com.awrank.web.model.domain.constant;

/**
 *
 */
public interface UserHistoryConst extends AbstractObjectConst {
    public static final String TABLE_NAME = "user_history";

    public static final String S_CREATED_HISTORY = "created_history_at";
    public static final String H_CREATED_HISTORY = "createdHistory";

    public static final String S_USER_ID = "user_id";
    public static final String H_USER_ID = "userId";

    public static final String S_API_KEY = "api_key";
    public static final String H_API_KEY = "apiKey";

    public static final String S_REF_USER_ID = "ref_user_id";
    public static final String H_REF_USER_ID = "refUserId";

    public static final String S_EMAIL = "email";
    public static final String H_EMAIL = "email";

    public static final String S_SKYPE = "skype";
    public static final String H_SKYPE = "skype";

    public static final String S_FIRST_NAME = "first_name";
    public static final String H_FIRST_NAME = "firstName";

    public static final String S_LAST_NAME = "last_name";
    public static final String H_LAST_NAME = "lastName";

    public static final String S_BIRTHDAY = "birthday";
    public static final String H_BIRTHDAY = "birthday";

    public static final String S_SECRET_QUESTION_DIC_CODE = "secret_question_dic_code";
    public static final String H_SECRET_QUESTION_DIC_CODE = "secretQuestionDicCode";

    public static final String S_SECRET_ANSWER = "secret_answer";
    public static final String H_SECRET_ANSWER = "secretAnswer";

    public static final String S_LANGUAGE = "language_code";
    public static final String H_LANGUAGE = "language";

    public static final String S_AUTHORIZATION_FAILS_COUNT = "authorization_fails_count";
    public static final String H_AUTHORIZATION_FAILS_COUNT = "authorizationFailsCount";

    public static final String S_AUTHORIZATION_FAILS_LAST_DATE = "authorization_fails_last";
    public static final String H_AUTHORIZATION_FAILS_LAST_DATE = "authorizationFailsLastDate";

    public static final String S_BAN_STARTED_DATE = "ban_started_at";
    public static final String H_BAN_STARTED_DATE = "banStartedDate";
}
