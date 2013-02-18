package com.awrank.web.backend.controller;

import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.utils.json.JsonUtils;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * No need in building JSON here: as far as all controllers MUST HAVE produces = "application/json",
 * headers = "content-type=application/x-www-form-urlencoded" in handlers the conversion Map <> JSON will be done
 * automatically, here we heed to handle Exception and build Map with its type and details etc.
 *
 * @author Alex Polyakov
 * @author Olga Korokhina
 */
public abstract class AbstractController {

	protected final void writeJsonObject(HttpServletResponse response, final ObjectNode jsonObject) throws IOException {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		Writer writer = response.getWriter();
		writer.write(jsonObject.toString());
		writer.close();
	}

	@ExceptionHandler(Exception.class)
	public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if (e instanceof UnauthorizedException) {
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		final ObjectNode jsonObject = new ObjectNode(JsonNodeFactory.instance);
		JsonUtils.set(jsonObject, "result", "failure");
		final ObjectNode jsonException = new ObjectNode(JsonNodeFactory.instance);
		JsonUtils.set(jsonException, "exception", e.getClass().getCanonicalName());
		JsonUtils.set(jsonException, "message", e.getMessage());
		JsonUtils.set(jsonException, "handler", this.getClass().getCanonicalName());
		StringWriter writer = new StringWriter();
		e.printStackTrace(new PrintWriter(writer));
		JsonUtils.set(jsonException, "stack_trace", writer.toString());
		jsonObject.put("error", jsonException);
		writeJsonObject(response, jsonObject);
	}
}
