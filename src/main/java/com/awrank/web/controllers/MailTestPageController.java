package com.awrank.web.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;
import java.util.HashMap;

@Controller
public class MailTestPageController {

	@Value("#{emailProps[SMTP_HOST_NAME]}")
	private String smpt_host_name;
	
	@Value("#{emailProps[SMTP_PORT]}")
	private String smpt_port; 
	
	@Value("#{emailProps[SMTP_USER_NAME]}")
	private String smpt_user_name; 
	
	@Value("#{emailProps[SMTP_PASSWORD]}")
	private String smpt_password; 
	
	@RequestMapping("/mailtest")
    public ModelAndView showPlayersGrid(HttpServletRequest request,
            HttpServletResponse response) {
      
    	ModelAndView mav = new ModelAndView("mailtest"); 
    	Map modelMap = new HashMap();
    	
    	modelMap.put("smpt_host_name", smpt_host_name);
    	modelMap.put("smpt_port", smpt_port);
    	modelMap.put("smpt_user_name", smpt_user_name);
    	modelMap.put("smpt_password", smpt_password);
    	
        mav.addAllObjects(modelMap);
        return mav;
    }

}
