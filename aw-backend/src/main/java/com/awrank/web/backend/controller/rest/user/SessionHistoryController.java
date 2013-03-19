package com.awrank.web.backend.controller.rest.user;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.backend.exception.ForbiddenException;
import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.dao.pojos.SessionHistoryFormEntryHistoryPojo;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author Alex Polyakov
 */
@Controller
@RequestMapping(value = "/rest/user/session_history")
public class SessionHistoryController extends AbstractController {
	@Autowired
	private EntryHistoryService entryHistoryService;

	/**
	 * Returns payment history
	 */
	@RequestMapping(method = RequestMethod.GET, produces = "application/json")
	public
	@ResponseBody
	List<SessionHistoryFormEntryHistoryPojo> list() throws UnauthorizedException, ForbiddenException {
		AWRankingUserDetails details = getUserDetails();
		checkHasAnyRole(Role.ROLE_ADMIN, Role.ROLE_USER);
		List<SessionHistoryFormEntryHistoryPojo> list = entryHistoryService.getSessionHistoryLast100(details.getUserId());
		return list;
	}
}
