package com.awrank.web.model.service;

import com.awrank.web.model.domain.EntryPoint;
import com.awrank.web.model.domain.StateChangeToken;
import com.awrank.web.model.exception.AwRankException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface StateChangeTokenService extends AbstractService {

	void send(Map params) throws AwRankException;

	EntryPoint verify(String key, HttpServletRequest request) throws AwRankException;

	void save(StateChangeToken act) throws AwRankException;

	StateChangeToken findByCode(String code);

}
