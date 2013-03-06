package com.awrank.web.model.domain;

import com.awrank.web.model.domain.support.ExtendedAbstractAuditable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The <b>Dictionary</b> class represents an dictionary entry.
 *
 * @author Alex Polyakov
 * @author Eugene Solomka
 * @author Olga Korokhina
 * @author Andrew Stoyaltsev
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "dictionary", uniqueConstraints = {
		@UniqueConstraint(columnNames = {"language", "code"})
})
@JsonIgnoreProperties({"new", "createdDate", "lastModifiedDate", "createdBy", "lastModifiedBy"})
public class Dictionary extends ExtendedAbstractAuditable {
	/**
	 * Language.
	 */
	@Column(name = "language", nullable = false, length = 2)
	@Enumerated(EnumType.STRING)
	private Language language;

	/**
	 * Message code in dictionary.
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

	public Dictionary(Long id, Language language, String code, String text) {
		setId(id);
		this.language = language;
		this.code = code;
		this.text = text;
	}

	/**
	 * @param code   {@code lngt} attribute in HTML element.
	 * @param enText English translation
	 * @param ruText Russian translation
	 * @return instance of {@link ArrayList} with couple of {@code Dictionary} objects.
	 */
	@Deprecated
	public static List<Dictionary> createItems(String code, String enText, String ruText) {
		List<Dictionary> dicList = new ArrayList<Dictionary>();
		dicList.add(new Dictionary(null, Language.EN, code, enText));
		dicList.add(new Dictionary(null, Language.RU, code, ruText));
		return dicList;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
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
