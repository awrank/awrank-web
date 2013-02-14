package com.awrank.web.backend.controller.pojos;

import java.io.Serializable;

public class TestJsonResultPOJO implements Serializable {
	
	private Integer id;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
//	private ELanguage language;
//
//	public ELanguage getLanguage() {
//		return language;
//	}
//	public void setLanguage(ELanguage language) {
//		this.language = language;
//	}
	private String code;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	private String text;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
