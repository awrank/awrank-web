package com.awrank.web.backend.controller;

import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.utils.json.JsonUtils;

import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Arrays;
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
	}

	protected final void writeMapToResponce(HttpServletResponse response, final Map map) throws IOException {

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		Writer writer = response.getWriter();
		writer.close();
		response.setHeader("Cache-Control", "no-cache");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		
		try {
			out = new ObjectOutputStream(bos);   
			out.writeObject(map);
			byte[] mapBytes = bos.toByteArray();
			OutputStream ostr = response.getOutputStream();
			ostr.write(mapBytes);
			ostr.flush();
		
		} finally {
			out.close();
			bos.close();
     }
	}
*/
	
	@ExceptionHandler(Exception.class)
	 public ResponseEntity<Map<String, String>> handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		Map<String, String> result = new HashMap<String, String>();
		 result.put("result", "failure");
		 result.put("reason", e.getClass().getSimpleName().toString());
		result.put("message", e.getMessage());
		result.put("handler", this.getClass().getSimpleName().toString());
		 result.put("stacktrace", Arrays.toString(e.getStackTrace()));

		return new ResponseEntity<Map<String, String> >(result, HttpStatus.INTERNAL_SERVER_ERROR);
		 
		 //writeMapToResponce(response, result);
		
		/*
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
		*/
	}
}
