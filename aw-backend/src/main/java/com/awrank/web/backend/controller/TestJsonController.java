package com.awrank.web.backend.controller;

import com.awrank.web.model.dao.dictionary.wrapper.DictionaryResource;
import com.awrank.web.model.domain.constant.ELanguage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.ws.rs.Consumes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: a_polyakov
 */
@Controller
public class TestJsonController {

//    @RequestMapping(value = "/testJson", method = RequestMethod.POST, produces = "application/json")
//    @Consumes("application/json")
//    public @ResponseBody
//    Dictionary showPlayersGrid(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "data", required = true) Map data) {
//
//        JsonNode test1= (JsonNode) data.get("test1");
//
//        Dictionary dictionary=new Dictionary(1L, ELanguage.RU, "test", "test_desc");
//        return dictionary;
//    }

    @SuppressWarnings("serial")
	private class TestJsonInput implements Serializable {
        private Object dictionary;//DictionaryResource

        /*
        private TestJsonInput() {
        	
        	dictionary = new Object();//DictionaryResource
        }
*/
        public Object getDictionary() {
            return dictionary;
        }

        public void setDictionary(Object dictionary) {
            this.dictionary = dictionary;
        }
    }


    @SuppressWarnings("serial")
	private class TestJsonResult implements Serializable {
        private List<ELanguage> languageList;

        public TestJsonResult() {
        	languageList = new ArrayList<ELanguage>();
        }

        public List<ELanguage> getLanguageList() {
            return languageList;
        }

        public void setLanguageList(List<ELanguage> languageList) {
            this.languageList = languageList;
        }
    }

    @RequestMapping(value = "/testJson", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
    @Consumes("application/json")
    public
    @ResponseBody()
    TestJsonResult testJson(@RequestBody TestJsonInput data) {
        data.getDictionary();
        TestJsonResult jsonObject = new TestJsonResult();
        jsonObject.setLanguageList(new ArrayList<ELanguage>(ELanguage.values().length));
        for (ELanguage item : ELanguage.values()) {
            jsonObject.getLanguageList().add(item);
        }
        return jsonObject;
    }
    
    @RequestMapping(value = "/testJson2", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json", headers = "Accept=application/json")
    @Consumes("application/json")
    public
    @ResponseBody()
    TestJsonResult testJson(@RequestBody String data) {
        //data.getDictionary();
        TestJsonResult jsonObject = new TestJsonResult();
        jsonObject.setLanguageList(new ArrayList<ELanguage>(ELanguage.values().length));
        for (ELanguage item : ELanguage.values()) {
            jsonObject.getLanguageList().add(item);
        }
        return jsonObject;
    }

//    @RequestMapping(value = "/testJson", method = RequestMethod.POST, produces = "application/json")
//    @Consumes("application/json")
//    public @ResponseBody() ObjectNode showPlayersGrid(HttpServletRequest request, HttpServletResponse response) {
//
//        final ObjectNode jsonObject = new ObjectNode(JsonNodeFactory.instance);
//        final ArrayNode jsonArray = new ArrayNode(JsonNodeFactory.instance);
//        for (ELanguage item : ELanguage.values()) {
//            jsonArray.add(item.toJsonObject());
//        }
//        jsonObject.put("languageList", jsonArray);
//        return jsonObject;
//    }
}
