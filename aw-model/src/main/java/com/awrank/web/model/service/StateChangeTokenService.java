package com.awrank.web.model.service;

import com.awrank.web.model.domain.StateChangeToken;
import com.awrank.web.model.exception.AwRankException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface StateChangeTokenService extends AbstractService {

	void send(Map params) throws AwRankException;

	Boolean verify(String key, HttpServletRequest request) throws AwRankException;

	void save(StateChangeToken act);

	StateChangeToken findByCode(String code);

}
