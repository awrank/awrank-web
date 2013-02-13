package com.awrank.web.backend.controller.rest;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.backend.controller.UrlConst;
import com.awrank.web.model.dao.dictionary.wrapper.DictionaryResource;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.domain.constant.ELanguage;
import com.awrank.web.model.exception.ObjectFieldException;
import com.awrank.web.model.exception.ObjectNotUniqueException;
import com.awrank.web.model.service.dictionary.DictionaryService;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Controller exposes basic methods to work with dictionary through REST.
 */
@Controller
@RequestMapping(value = "/rest/dictionary")
public class DictionaryController extends AbstractController {
    @Autowired
    private DictionaryService dictionaryService;

    /**
     * Returns paginated list of dictionary entries
     */
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody Page<Dictionary> list(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "25") int size) {
        return dictionaryService.findAll(new PageRequest(page, size));
    }

    /**
     * Creates new dictionary entry
     */
    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json", produces = "application/json")
    public @ResponseBody Dictionary create(@RequestBody Dictionary body) {
        return body;

    }


//
//    @RequestMapping(value = "/" + UrlConst.URL_DICTIONARY_LIST, method = RequestMethod.POST)
//    public void getDictionaryList(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        final ObjectNode jsonObject = new ObjectNode(JsonNodeFactory.instance);
//        final ArrayNode jsonArray = new ArrayNode(JsonNodeFactory.instance);
//        List<DictionaryResource> list = dictionaryService.getList();
//        for (DictionaryResource item : list) {
//            jsonArray.add(item.toJsonObject());
//        }
//        jsonObject.put("dictionaryList", jsonArray);
//
//        writeJsonObject(response, jsonObject);
//    }
//
//    @RequestMapping(value = "/" + UrlConst.URL_DICTIONARY_INSERT, method = RequestMethod.POST)
//    public void getDictionaryInsert(HttpServletRequest request, HttpServletResponse response) throws IOException, ObjectFieldException, ObjectNotUniqueException {
//        ObjectNode jsonObject = readJsonObject(request);
//        DictionaryResource dictionary = new DictionaryResource((ObjectNode) jsonObject.get("dictionary"));
//
//        jsonObject = new ObjectNode(JsonNodeFactory.instance);
//        dictionary = dictionaryService.insert(dictionary);
//        jsonObject.put("dictionary", dictionary.toJsonObject());
//
//        writeJsonObject(response, jsonObject);
//    }
//
//    @RequestMapping(value = "/" + UrlConst.URL_DICTIONARY_UPDATE, method = RequestMethod.POST)
//    public void getDictionaryUpdate(HttpServletRequest request, HttpServletResponse response) throws IOException, ObjectNotUniqueException, ObjectFieldException {
//        ObjectNode jsonObject = readJsonObject(request);
//        jsonObject = (ObjectNode) jsonObject.get("dictionary");
//        DictionaryResource dictionary = new DictionaryResource(jsonObject);
//
//        jsonObject = new ObjectNode(JsonNodeFactory.instance);
//        dictionaryService.update(dictionary);
//        writeJsonObject(response, jsonObject);
//    }
//
//    @RequestMapping(value = "/" + UrlConst.URL_DICTIONARY_DELETE, method = RequestMethod.POST)
//    public void getDictionaryDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ObjectFieldException {
//        ObjectNode jsonObject = readJsonObject(request);
//        DictionaryResource dictionary = new DictionaryResource((ObjectNode) jsonObject.get("dictionary"));
//
//        jsonObject = new ObjectNode(JsonNodeFactory.instance);
//        dictionaryService.delete(dictionary);
//        writeJsonObject(response, jsonObject);
//    }

}
