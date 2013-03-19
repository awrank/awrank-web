package com.awrank.web.model.dao;

import com.awrank.web.model.dao.pojos.SessionHistoryFormEntryHistoryPojo;

import java.util.List;

/**
 * @author Alex Polyakov
 */
public interface EntryHistoryCustomDao {
	public List<SessionHistoryFormEntryHistoryPojo> getSessionHistoryLast100(Long userId);
}
