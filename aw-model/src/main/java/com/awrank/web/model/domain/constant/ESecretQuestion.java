package com.awrank.web.model.domain.constant;

import com.awrank.web.model.constant.EMessageConst;

/**
 * User: a_polyakov
 */
public enum ESecretQuestion {
    SECRET_QUESTION_FAVORITE_NUMBER(EMessageConst.SECRET_QUESTION_FAVORITE_NUMBER),
    SECRET_QUESTION_OTHER(EMessageConst.SECRET_QUESTION_OTHER);

    private EMessageConst value;

    private ESecretQuestion(EMessageConst messageConst) {
        this.value = messageConst;
    }
}
