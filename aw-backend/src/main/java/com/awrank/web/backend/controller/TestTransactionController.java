package com.awrank.web.backend.controller;

import com.awrank.web.model.domain.constant.ELanguage;
import com.awrank.web.model.service.dictionary.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Controller
public class TestTransactionController {

    @Autowired
    private DictionaryService dictionaryService;

//    @RequestMapping("/testTransaction")
//    public ModelAndView showPlayersGrid(HttpServletRequest request,
//                                        HttpServletResponse response) {
//
//        ModelAndView mav = new ModelAndView("test_transaction");
//
//        dictionaryService.create();
//        return mav;
//    }

//    @RequestMapping("/testTransaction")
//    public @ResponseBody JsonObject showPlayersGrid(HttpServletRequest request,
//                                                    HttpServletResponse response) {
//
//        ModelAndView mav = new ModelAndView("test_transaction");
//
//        Dictionary dictionary=new Dictionary();
//        dictionary.setLanguage(ELanguage.RU);
//        dictionary.setCode("ERROR");
//        dictionary.setText("ERROR1");
//        return dictionary.toJsonObject();
//    }

    @RequestMapping("/testTransaction")
    public
    @ResponseBody
    Map showPlayersGrid(HttpServletRequest request,
                        HttpServletResponse response) {
        Map map = new HashMap();
        map.put("Language", ELanguage.RU);
        map.put("Code", "ERROR");
        map.put("Text", "ERROR1");
        return map;
    }

}
