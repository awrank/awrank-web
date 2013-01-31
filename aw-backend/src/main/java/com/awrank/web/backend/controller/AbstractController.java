package com.awrank.web.backend.controller;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * User: a_polyakov
 */
public abstract class AbstractController {

    private Gson gson = new Gson();

    protected final JsonObject readJsonObject(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
//        Gson gson;
//        final GsonBuilder gsonbuilder = new GsonBuilder();
//        gson = gsonbuilder.create();
        JsonObject jsonObject = (JsonObject) gson.fromJson(request.getParameter("data"), JsonElement.class);
        return jsonObject;
    }

    protected final void writeJsonObject(HttpServletResponse response, final JsonObject jsonObject) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Writer writer = response.getWriter();
        writer.write(jsonObject.toString());
        writer.close();
    }
}
