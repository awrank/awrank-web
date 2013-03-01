package com.awrank.web.model.service;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.awrank.web.model.domain.StateChangeToken;
import com.awrank.web.model.exception.AwRankException;

public interface StateChangeTokenService {
	
	 	void send(Map params) throws AwRankException;

	    Boolean verify(String key, HttpServletRequest request) throws AwRankException;

	    void save(StateChangeToken act);

	    StateChangeToken findByCode(String code);

}
