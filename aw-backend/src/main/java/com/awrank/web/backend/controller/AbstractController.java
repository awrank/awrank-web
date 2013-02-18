package com.awrank.web.backend.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * User: a_polyakov
 * Refactered by Olga
 * No need in building JSON here: as far as all controllers MUST HAVE produces = "application/json", headers = "content-type=application/x-www-form-urlencoded"
 * in handlers the conversion Map <> JSON will be done automatically, here we heed to handle Exception and build Map with it's type and details etc. 
 */

public abstract class AbstractController {

    //write pure Object (HashMap in our case) to response - will be concerted to JSON
    protected final void writeMapToResponce(HttpServletResponse response, final Map map) throws IOException {
    	
    	response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.setHeader("Cache-Control", "no-cache");
        
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        
        try {
        	
          out = new ObjectOutputStream(bos);   
          out.writeObject(map);
        
          byte[] mapBytes = bos.toByteArray();
          
          OutputStream ostr = response.getOutputStream();
          
          ostr.write(mapBytes);
          ostr.flush();
          
        
        } finally {
          out.close();
          bos.close();
        }
        
    }

	@ExceptionHandler(Exception.class)
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
    	
    	Map<String, String> result = new HashMap<String, String>();
		result.put("result", "failure");
		result.put("reason", e.getClass().getSimpleName().toString());
		result.put("message", e.getMessage());
		result.put("handler", this.getClass().getSimpleName().toString());
		result.put("stacktrace", e.getStackTrace().toString());
    	
		writeMapToResponce(response, result);
    }
}
