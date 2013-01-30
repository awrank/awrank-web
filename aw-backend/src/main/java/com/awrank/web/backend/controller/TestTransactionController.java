package com.awrank.web.backend.controller;

import com.awrank.web.model.service.testTransaction.TestTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 */
@Controller
public class TestTransactionController {

    @Autowired
    private TestTransactionService testTransactionService;

    @RequestMapping("/testTransaction")
    public ModelAndView showPlayersGrid(HttpServletRequest request,
                                        HttpServletResponse response) {

        ModelAndView mav = new ModelAndView("test_transaction");

        testTransactionService.create();
        return mav;
    }

}
