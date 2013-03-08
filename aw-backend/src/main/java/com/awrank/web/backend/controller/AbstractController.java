package com.awrank.web.backend.controller;

import com.awrank.web.backend.exception.ForbiddenException;
import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.enums.Role;
import com.awrank.web.model.service.jopos.AWRankingUserDetails;
import com.awrank.web.model.utils.user.AuditorAwareImpl;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * No need in building JSON here: as far as all controllers MUST HAVE produces = "application/json",
 * headers = "content-type=application/x-www-form-urlencoded" in handlers the conversion Map <> JSON will be done
 * automatically, here we heed to handle Exception and build Map with its type and details etc.
 *
 * @author Alex Polyakov
 * @author Olga Korokhina
 */
public abstract class AbstractController {

	/*
	protected final void writeJsonObject(HttpServletResponse response, final ObjectNode jsonObject) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		Writer writer = response.getWriter();
		writer.write(jsonObject.toString());
		writer.close();
	}  */

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String, Object>> handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
		getLogger().error(e.getMessage(), e);
		final Map jsonObject = new HashMap();
		jsonObject.put("result", "failure");
		final Map jsonException = new HashMap();

		jsonException.put("message", e.getMessage());
		jsonException.put("handler", this.getClass().getCanonicalName());
		jsonException.put("exception", e.getClass().getCanonicalName());
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		jsonException.put("stack_trace", writer.toString());
		jsonObject.put("error", jsonException);
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		if (e instanceof UnauthorizedException || e.getCause() instanceof UnauthorizedException) {
			status = HttpStatus.UNAUTHORIZED;
		} else if (e instanceof ForbiddenException || e.getCause() instanceof ForbiddenException) {
			status = HttpStatus.FORBIDDEN;
		}

		return new ResponseEntity<Map<String, Object>>(jsonObject, status);
	}

	public Logger getLogger() {
		return Logger.getLogger(this.getClass());
	}

	protected AWRankingUserDetails getUserDetails() throws UnauthorizedException {
		AWRankingUserDetails userDetails = AuditorAwareImpl.getCurrentUserDetails();
		if (userDetails == null)
			throw UnauthorizedException.getInstance();
		return userDetails;
	}

	protected void checkHasRole(Role role) throws ForbiddenException, UnauthorizedException {
//      @PreAuthorize("hasRole('ROLE_USER')") not work
//		request.isUserInRole()
//		SecurityContextHolderAwareRequestWrapper.isUserInRole(role.name());
		if (getUserDetails().hasRole(role))
			throw ForbiddenException.getInstance();
	}

	protected void checkHasAnyRole(Role... roles) throws ForbiddenException, UnauthorizedException {
		for (Role role : roles) {
			if (getUserDetails().hasRole(role))
				return;
		}
		throw ForbiddenException.getInstance();
	}
}
