package com.awrank.web.backend.controller;

import com.awrank.web.model.dao.dictionary.wrapper.DictionaryWrapper;
import com.awrank.web.model.domain.constant.ELanguage;
import com.awrank.web.model.exception.AwRankModelException;
import com.awrank.web.model.service.dictionary.DictionaryService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * User: a_polyakov
 */
@Controller
public class DictionaryController extends AbstractController {

    @Autowired
    private DictionaryService dictionaryService;

    @RequestMapping(value = "/" + UrlConst.URL_LANGUAGE_LIST, method = RequestMethod.POST)
    public void getLanguageList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final JsonObject jsonObject = new JsonObject();
        final JsonArray jsonArray = new JsonArray();
        for (ELanguage item : ELanguage.values()) {
            jsonArray.add(item.toJsonObject());
        }
        jsonObject.add("languageList", jsonArray);

        writeJsonObject(response, jsonObject);
    }

    @RequestMapping(value = "/" + UrlConst.URL_DICTIONARY_LIST, method = RequestMethod.POST)
    public void getDictionaryList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final JsonObject jsonObject = new JsonObject();
        final JsonArray jsonArray = new JsonArray();
        List<DictionaryWrapper> list = dictionaryService.getList();
        for (DictionaryWrapper item : list) {
            jsonArray.add(item.toJsonObject());
        }
        jsonObject.add("dictionaryList", jsonArray);

        writeJsonObject(response, jsonObject);
    }

    @RequestMapping(value = "/" + UrlConst.URL_DICTIONARY_INSERT, method = RequestMethod.POST)
    public void getDictionaryInsert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject jsonObject = readJsonObject(request);
        DictionaryWrapper dictionary = new DictionaryWrapper(jsonObject.getAsJsonObject("dictionary"));

        jsonObject = new JsonObject();
        try {
            dictionary = dictionaryService.insert(dictionary);
            jsonObject.add("dictionary", dictionary.toJsonObject());
        } catch (AwRankModelException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonObject.add("error", e.toJsonObject());
        }

        writeJsonObject(response, jsonObject);
    }

    @RequestMapping(value = "/" + UrlConst.URL_DICTIONARY_UPDATE, method = RequestMethod.POST)
    public void getDictionaryUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject jsonObject = readJsonObject(request);
        jsonObject = jsonObject.getAsJsonObject("dictionary");
        DictionaryWrapper dictionary = new DictionaryWrapper(jsonObject);

        jsonObject = new JsonObject();
        try {
            dictionaryService.update(dictionary);
        } catch (AwRankModelException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonObject.add("error", e.toJsonObject());
        }

        writeJsonObject(response, jsonObject);
    }

    @RequestMapping(value = "/" + UrlConst.URL_DICTIONARY_DELETE, method = RequestMethod.POST)
    public void getDictionaryDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        JsonObject jsonObject = readJsonObject(request);
        DictionaryWrapper dictionary = new DictionaryWrapper(jsonObject.getAsJsonObject("dictionary"));

        jsonObject = new JsonObject();
        try {
            dictionaryService.delete(dictionary);
        } catch (AwRankModelException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonObject.add("error", e.toJsonObject());
        }

        writeJsonObject(response, jsonObject);
    }

}
