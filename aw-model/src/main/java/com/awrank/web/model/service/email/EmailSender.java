/**
 * 
 */
package com.awrank.web.model.service.email;

import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @author Olga Korokhina
 *
 */
@Service
public interface EmailSender {
	
	 public void send(String template, Map<String,Object> params) throws Exception;

}
