package com.awrank.web.backend.controller;

import javax.ws.rs.Consumes;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.awrank.web.backend.controller.pojos.TestJsonInputPOJO;
//import com.awrank.web.backend.controller.pojos.TestJsonResultPOJO;

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

	/*
    @RequestMapping(value = "/testJson", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
    @Consumes("application/json")
    public
    @ResponseBody()
    TestJsonResultPOJO testJson(@RequestBody TestJsonInputPOJO data) {
        
    	System.out.println(data.getDictionary());
    	
    	return data.getDictionary();
    
    }
    */
 
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
