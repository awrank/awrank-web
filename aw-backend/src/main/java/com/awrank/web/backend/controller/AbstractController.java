package com.awrank.web.backend.controller;

import com.awrank.web.backend.exception.UnauthorizedException;
import com.awrank.web.model.exception.AwRankException;
import com.awrank.web.model.exception.AwRankModelException;
import org.codehaus.jackson.map.ObjectMapper;
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
 * User: a_polyakov
 */
// DOTO: refactor this out!
public abstract class AbstractController {

    private ObjectMapper objectMapper = new ObjectMapper();

    protected final ObjectNode readJsonObject(HttpServletRequest request) throws IOException {
        request.setCharacterEncoding("UTF-8");
        ObjectNode jsonObject = objectMapper.readValue(request.getParameter("data"), ObjectNode.class);
        return jsonObject;
    }

    protected final void writeJsonObject(HttpServletResponse response, final ObjectNode jsonObject) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Writer writer = response.getWriter();
       // writer.write(jsonObject.toString());
        writer.close();
    }

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    	/*
        final ObjectNode jsonObject = new ObjectNode(JsonNodeFactory.instance);
        if (e instanceof UnauthorizedException) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            jsonObject.put("error", ((UnauthorizedException) e).toJsonObject());
        } else if (e instanceof AwRankException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonObject.put("error", ((AwRankModelException) e).toJsonObject());
        } else if (e instanceof NullPointerException) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ObjectNode errorJson = new ObjectNode(JsonNodeFactory.instance);
            JsonUtils.set(jsonObject, "exception", e.getClass().getCanonicalName());
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            JsonUtils.set(jsonObject, "stackTrace", writer.toString());
            jsonObject.put("error", errorJson);
        } else {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            ObjectNode errorJson = new ObjectNode(JsonNodeFactory.instance);
            JsonUtils.set(jsonObject, "exception", e.getClass().getCanonicalName());
            JsonUtils.set(jsonObject, "message", e.getMessage());
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            JsonUtils.set(jsonObject, "stackTrace", writer.toString());
            jsonObject.put("error", errorJson);
        }
        */
        //writeJsonObject(response, jsonObject);
    	writeJsonObject(response, null);
    }
}
