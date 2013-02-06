package com.awrank.web.model.dao.dictionary.wrapper;

import java.io.Serializable;

import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.constant.ELanguage;
import com.awrank.web.model.utils.json.IJsonObject;
import com.awrank.web.model.utils.json.JsonUtils;
import com.awrank.web.model.utils.select.annotation.SelectField;
import com.awrank.web.model.utils.select.annotation.SelectFrom;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

/**
 * User: a_polyakov
 */
@SuppressWarnings("serial")
public class DictionaryResource implements Serializable, IJsonObject {
    private Long id;
    private ELanguage language;
    private String code;
    private String text;

    public DictionaryResource() {
    }

    @SelectFrom(Dictionary.class)
    public DictionaryResource(
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

    public void setId(Long id) {
        this.id = id;
    }

    public ELanguage getLanguage() {
        return language;
    }

    public void setLanguage(ELanguage language) {
        this.language = language;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // --------------------------- JSON ------------------------------------------

    public DictionaryResource(final ObjectNode jsonObject) {
        this.id = JsonUtils.getLong(jsonObject, Dictionary.S_ID);
        this.language = JsonUtils.getEnum(jsonObject, Dictionary.S_LANGUAGE, ELanguage.class);
        this.code = JsonUtils.getString(jsonObject, Dictionary.S_CODE);
        this.text = JsonUtils.getString(jsonObject, Dictionary.S_TEXT);
    }

    @Override
    public ObjectNode toJsonObject() {
        final ObjectNode jsonObject = new ObjectNode(JsonNodeFactory.instance);
        JsonUtils.set(jsonObject, Dictionary.S_ID, id);
        JsonUtils.set(jsonObject, Dictionary.S_LANGUAGE, language);
        JsonUtils.set(jsonObject, Dictionary.S_CODE, code);
        JsonUtils.set(jsonObject, Dictionary.S_TEXT, text);
        return jsonObject;
    }

    @Override
    public String toString() {
        return toJsonObject().toString();
    }
}
