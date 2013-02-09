package com.awrank.web.backend.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.awrank.web.backend.controller.pojos.TestJsonInputPOJO;
import com.awrank.web.backend.controller.pojos.TestJsonResultPOJO;

/**
 * @author Olga Korokhina
 * The idea is to have one Controller for all test pages with UI (forms etc.)- and if needed redirect from it to particular pages.
 * So it would be easier to get rid of them later
 *
 */
@Controller
public class TestPagesController {
	
	@RequestMapping("/mailtest")
	public String showMailTestPage(HttpServletRequest request,
										HttpServletResponse response) {

		return "redirect:mail";
	}
	
	
	@RequestMapping(value = "/testJson", method = RequestMethod.POST, produces = "application/json", headers = "Accept=application/json")
	    @Consumes("application/json")
	    public
	    @ResponseBody()
	    TestJsonResultPOJO testJson(@RequestBody TestJsonInputPOJO data) {
	        
	    	System.out.println(data.getDictionary());
	    	
	    	return data.getDictionary();
	    
	    }
	
	@RequestMapping("/register")
	public ModelAndView showRegisterTestPage(HttpServletRequest request,
										HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("register");
		
		return mav;
	}

}
