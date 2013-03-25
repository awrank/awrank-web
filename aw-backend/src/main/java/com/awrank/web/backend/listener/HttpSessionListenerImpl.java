package com.awrank.web.backend.listener;

import com.awrank.web.model.service.EntryHistoryService;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Alex Polyakov
 */
//@WebListener
public class HttpSessionListenerImpl implements HttpSessionListener {

	private static Logger LOG = Logger.getLogger(HttpSessionListenerImpl.class);

	@Override
	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
		LOG.debug("session create");
		//TODO
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		LOG.debug("session destroy");
		HttpSession httpSession = httpSessionEvent.getSession();
		if (httpSession != null) {
			Object o = httpSession.getAttribute("SPRING_SECURITY_CONTEXT");
			if (o instanceof SecurityContext) {
				SecurityContext securityContext = (SecurityContext) o;
				Authentication authentication = securityContext.getAuthentication();
				if (authentication != null) {
					o = authentication.getPrincipal();
					if (o instanceof AWRankingUserDetails) {
						AWRankingUserDetails details = (AWRankingUserDetails) o;
						ApplicationContext applicationContext = ApplicationContextAwareImpl.getApplicationContext();
						if (applicationContext != null) {
							EntryHistoryService entryHistoryService = applicationContext.getBean(EntryHistoryService.class);
							if (entryHistoryService != null) {
								entryHistoryService.logoutUser(details.getEntryHistoryId());
							}
						}
					}
				}
			}
		}
	}
}
