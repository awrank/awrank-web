package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.DictionaryConst;
import com.awrank.web.model.domain.constant.ELanguage;
import com.awrank.web.model.domain.constant.EObjectType;
import com.awrank.web.model.utils.json.JsonUtils;
import com.google.gson.JsonObject;

import javax.persistence.*;

/**
 * словарь
 */
@Entity
@Table(name = DictionaryConst.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = {DictionaryConst.S_LANGUAGE, DictionaryConst.S_CODE})
})
public class Dictionary extends AbstractObject implements DictionaryConst {
    /**
     * язык
     */
    private ELanguage language;
    /**
     * код текста в словаре
     */
    private String code;
    /**
     * текст
     */
    private String text;

    {
        objectType = EObjectType.DICTIONARY;
    }

    public Dictionary() {
    }

    public Dictionary(Long id, ELanguage language, String code, String text) {
        super(id);
        this.language = language;
        this.code = code;
        this.text = text;
    }

    @Column(name = S_LANGUAGE, nullable = false)
    @Enumerated(EnumType.STRING)
    public ELanguage getLanguage() {
        return language;
    }

    public void setLanguage(ELanguage language) {
        this.language = language;
    }

    public void setLanguage(String language) {
        this.language = (language != null) ? ELanguage.valueOf(language) : null;
    }

    @Column(name = S_CODE, nullable = false)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Column(name = S_TEXT, nullable = false)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // --------------------------- JSON ------------------------------------------

    public Dictionary(final JsonObject jsonObject) {
        super(jsonObject);
        this.language = JsonUtils.getEnum(jsonObject, S_LANGUAGE, ELanguage.class);
        this.code = JsonUtils.getString(jsonObject, S_CODE);
        this.text = JsonUtils.getString(jsonObject, S_TEXT);
    }

    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = super.toJsonObject();
        JsonUtils.set(jsonObject, S_LANGUAGE, language);
        JsonUtils.set(jsonObject, S_CODE, code);
        JsonUtils.set(jsonObject, S_TEXT, text);
        return jsonObject;
    }

}
