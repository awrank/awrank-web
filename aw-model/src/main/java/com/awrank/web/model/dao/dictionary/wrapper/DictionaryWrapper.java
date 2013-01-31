package com.awrank.web.model.dao.dictionary.wrapper;

import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.constant.ELanguage;
import com.awrank.web.model.utils.json.IJsonObject;
import com.awrank.web.model.utils.json.JsonUtils;
import com.awrank.web.model.utils.select.annotation.SelectField;
import com.awrank.web.model.utils.select.annotation.SelectFrom;
import com.google.gson.JsonObject;

/**
 * User: a_polyakov
 */
public class DictionaryWrapper implements IJsonObject {
    private final Long id;
    private final ELanguage language;
    private final String code;
    private final String text;

    @SelectFrom(Dictionary.class)
    public DictionaryWrapper(
            @SelectField(Dictionary.H_ID) Long id,
            @SelectField(Dictionary.H_LANGUAGE) ELanguage language,
            @SelectField(Dictionary.H_CODE) String code,
            @SelectField(Dictionary.H_TEXT) String text) {
        this.id = id;
        this.language = language;
        this.code = code;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public ELanguage getLanguage() {
        return language;
    }

    public String getCode() {
        return code;
    }

    public String getText() {
        return text;
    }

    // --------------------------- JSON ------------------------------------------

    public DictionaryWrapper(final JsonObject jsonObject) {
        this.id = JsonUtils.getLong(jsonObject, Dictionary.S_ID);
        this.language = JsonUtils.getEnum(jsonObject, Dictionary.S_LANGUAGE, ELanguage.class);
        this.code = JsonUtils.getString(jsonObject, Dictionary.S_CODE);
        this.text = JsonUtils.getString(jsonObject, Dictionary.S_TEXT);
    }

    @Override
    public JsonObject toJsonObject() {
        final JsonObject jsonObject = new JsonObject();
        JsonUtils.set(jsonObject, Dictionary.S_ID, id);
        JsonUtils.set(jsonObject, Dictionary.S_LANGUAGE, language);
        JsonUtils.set(jsonObject, Dictionary.S_CODE, code);
        JsonUtils.set(jsonObject, Dictionary.S_TEXT, text);
        return jsonObject;
    }
}
