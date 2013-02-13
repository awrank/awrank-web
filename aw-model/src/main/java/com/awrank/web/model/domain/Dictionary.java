package com.awrank.web.model.domain;

import com.awrank.web.model.domain.constant.ELanguage;
import com.awrank.web.model.domain.support.ExtendedAbstractAuditable;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * The <b>Dictionary</b> class represents an dictionary entry.
 */
@Entity
@Table(name = "dictionary", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"language", "code"})
})
@JsonIgnoreProperties(value = {"id", "createdDate", "lastModifiedDate", "createdBy", "lastModifiedBy"})
public class Dictionary extends ExtendedAbstractAuditable<Long> {
    /**
     * Language
     */
    @Column(name = "language", nullable = false)
    @Enumerated(EnumType.STRING)
    private ELanguage language;

    /**
     * Message code in dictionary
     */
    @Column(name = "code", nullable = false, length = 64)
    private String code;

    /**
     * Text of the message.
     */
    @Column(name = "text", nullable = false, length = 1024)
    private String text;


    public Dictionary() {
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
}
