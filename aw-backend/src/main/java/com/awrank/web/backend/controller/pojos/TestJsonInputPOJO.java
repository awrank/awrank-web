package com.awrank.web.backend.controller.pojos;

import java.io.Serializable;
import java.util.Map;


@SuppressWarnings("serial")
public class TestJsonInputPOJO implements Serializable {
	
	 private TestJsonResultPOJO dictionary;
	 
	 public void setDictionary(TestJsonResultPOJO value){
		 
		 dictionary = value; 
	 }
	 
	 public TestJsonResultPOJO getDictionary(){
		 
		 return dictionary;
	 }
	
	
}
