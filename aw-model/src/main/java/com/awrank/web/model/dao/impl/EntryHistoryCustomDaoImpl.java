package com.awrank.web.model.dao.impl;

import com.awrank.web.model.dao.EntryHistoryCustomDao;
import com.awrank.web.model.dao.pojos.SessionHistoryFormEntryHistoryPojo;
import com.awrank.web.model.domain.constant.EntryHistoryConst;
import com.awrank.web.model.utils.select.SelectUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Alex Polyakov
 */
@Repository
public class EntryHistoryCustomDaoImpl extends AbstractDaoImpl implements EntryHistoryCustomDao {
	@Override
	public List<SessionHistoryFormEntryHistoryPojo> getSessionHistoryLast100(Long userId) {
		List<SessionHistoryFormEntryHistoryPojo> list = SelectUtils.getWrapperList(em, SessionHistoryFormEntryHistoryPojo.class,
				"where o." + EntryHistoryConst.H_USER__ID + '=' + userId +
						" order by o." + EntryHistoryConst.H_SIGNIN_DATE + " desc", 0, 100);
		return list;
	}
}
