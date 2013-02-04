package com.awrank.web.backend.controller;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * User: a_polyakov
 */
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
        writer.write(jsonObject.toString());
        writer.close();
    }
}
