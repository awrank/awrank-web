package com.awrank.web.backend.controller;

import com.awrank.web.model.dao.dictionary.wrapper.DictionaryResource;
import com.awrank.web.model.domain.constant.ELanguage;
import com.awrank.web.model.exception.AwRankModelException;
import com.awrank.web.model.service.dictionary.DictionaryService;
import com.awrank.web.model.utils.json.JsonUtils;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
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
        final ObjectNode jsonObject = new ObjectNode(JsonNodeFactory.instance);
        final ArrayNode jsonArray = new ArrayNode(JsonNodeFactory.instance);
        for (ELanguage item : ELanguage.values()) {
            jsonArray.add(item.toJsonObject());
        }
        jsonObject.put("languageList", jsonArray);

        writeJsonObject(response, jsonObject);
    }

    @RequestMapping(value = "/" + UrlConst.URL_DICTIONARY_LIST, method = RequestMethod.POST)
    public void getDictionaryList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final ObjectNode jsonObject = new ObjectNode(JsonNodeFactory.instance);
        final ArrayNode jsonArray = new ArrayNode(JsonNodeFactory.instance);
        List<DictionaryResource> list = dictionaryService.getList();
        for (DictionaryResource item : list) {
            jsonArray.add(item.toJsonObject());
        }
        jsonObject.put("dictionaryList", jsonArray);

        writeJsonObject(response, jsonObject);
    }

    @RequestMapping(value = "/" + UrlConst.URL_DICTIONARY_INSERT, method = RequestMethod.POST)
    public void getDictionaryInsert(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectNode jsonObject = readJsonObject(request);
        DictionaryResource dictionary = new DictionaryResource((ObjectNode) jsonObject.get("dictionary"));

        jsonObject = new ObjectNode(JsonNodeFactory.instance);
        try {
            dictionary = dictionaryService.insert(dictionary);
            jsonObject.put("dictionary", dictionary.toJsonObject());
        } catch (AwRankModelException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonObject.put("error", e.toJsonObject());
        } catch (Exception e) {
            ObjectNode errorJson = new ObjectNode(JsonNodeFactory.instance);
            JsonUtils.set(jsonObject, "exception", e.getClass().getCanonicalName());
            JsonUtils.set(jsonObject, "message", e.getMessage());
            jsonObject.put("error", errorJson);
        }

        writeJsonObject(response, jsonObject);
    }

    @RequestMapping(value = "/" + UrlConst.URL_DICTIONARY_UPDATE, method = RequestMethod.POST)
    public void getDictionaryUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectNode jsonObject = readJsonObject(request);
        jsonObject = (ObjectNode) jsonObject.get("dictionary");
        DictionaryResource dictionary = new DictionaryResource(jsonObject);

        jsonObject = new ObjectNode(JsonNodeFactory.instance);
        try {
            dictionaryService.update(dictionary);
        } catch (AwRankModelException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonObject.put("error", e.toJsonObject());
        } catch (Exception e) {
            ObjectNode errorJson = new ObjectNode(JsonNodeFactory.instance);
            JsonUtils.set(jsonObject, "exception", e.getClass().getCanonicalName());
            JsonUtils.set(jsonObject, "message", e.getMessage());
            jsonObject.put("error", errorJson);
        }

        writeJsonObject(response, jsonObject);
    }

    @RequestMapping(value = "/" + UrlConst.URL_DICTIONARY_DELETE, method = RequestMethod.POST)
    public void getDictionaryDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectNode jsonObject = readJsonObject(request);
        DictionaryResource dictionary = new DictionaryResource((ObjectNode) jsonObject.get("dictionary"));

        jsonObject = new ObjectNode(JsonNodeFactory.instance);
        try {
            dictionaryService.delete(dictionary);
        } catch (AwRankModelException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            jsonObject.put("error", e.toJsonObject());
        } catch (Exception e) {
            ObjectNode errorJson = new ObjectNode(JsonNodeFactory.instance);
            JsonUtils.set(jsonObject, "exception", e.getClass().getCanonicalName());
            JsonUtils.set(jsonObject, "message", e.getMessage());
            jsonObject.put("error", errorJson);
        }

        writeJsonObject(response, jsonObject);
    }

}
