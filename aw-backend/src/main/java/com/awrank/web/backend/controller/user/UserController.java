package com.awrank.web.backend.controller.user;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.User;

import com.awrank.web.model.exception.emailactivation.UserActivationEmailNotSetException;
import com.awrank.web.model.exception.entrypoint.EntryPointNotCreatedException;
import com.awrank.web.model.exception.user.UserNotCreatedException;
import com.awrank.web.model.exception.user.UserNotDeletedException;
import com.awrank.web.model.service.UserEmailActivationService;
import com.awrank.web.model.service.UserService;
import com.awrank.web.model.service.impl.UserServiceImpl;
import com.awrank.web.model.service.impl.pojos.UserRegistrationFormPojo;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Olga Korokhina
 *         The stub for work with users
 */
@Controller
@RequestMapping(value = "/user")
public class UserController extends AbstractController {

    @Autowired
    UserService userService;
    
    @Autowired
    UserEmailActivationService userEmailActivationService;

    private Map getPositiveResponseMap() {

        Map result = new HashMap<String, String>();
        result.put("result", "ok");

        return result;
    }

    private Map getNegativeResponseMap(String reason) {

        Map result = new HashMap<String, String>();
        result.put("result", "failure");
        result.put("reason", reason);

        return result;
    }

   /**
    *  Here we go from registartion form so, technically, this is "/register"
    * @param form
    * @param request
    * @return
    * @throws EntryPointNotCreatedException
    * @throws UserActivationEmailNotSetException
    */
    @RequestMapping(value = "/add", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json", headers = "content-type=application/x-www-form-urlencoded")
    public
    @ResponseBody()
    Map addUser(@ModelAttribute UserRegistrationFormPojo form, HttpServletRequest request) throws EntryPointNotCreatedException, UserActivationEmailNotSetException {

        if (userService.findByEmail(form.getEmail()).size() > 0)
            return getNegativeResponseMap("this email already registered in system");
        if (userService.findByAPIKey(form.getApiKey()).size() > 0)
            return getNegativeResponseMap("this apikey already registered in system");

        form.setUserLocalAddr(request.getLocalAddr());
        form.setUserRemoteAddr(request.getRemoteAddr());

        try {
        	
            userService.register(form, request);

            return getPositiveResponseMap();

        } catch (UserNotCreatedException e) {

            e.printStackTrace();
            return getNegativeResponseMap(e.getMessage());
        }

    }

    //headers = "Accept=application/json"
    @RequestMapping(value = "/delete", method = {RequestMethod.POST, RequestMethod.GET}, produces = "application/json", headers = "content-type=application/x-www-form-urlencoded")
    //@Consumes("application/json")
    public
    @ResponseBody
    Map deleteUser(@RequestBody User user) {

        if (userService.findByEmail(user.getEmail()).size() == 0)
            return getNegativeResponseMap("user with this email not registered in system");
        if (userService.findById(user.getId()).size() == 0)
            return getNegativeResponseMap("user with this ID not registered in system");

        try {

            userService.delete(user);

            return getPositiveResponseMap();

        } catch (UserNotDeletedException e) {

            e.printStackTrace();
            return getNegativeResponseMap(e.getMessage());
        }
    }


    @RequestMapping(method = RequestMethod.GET, value = "/verifyemail/{key}")
    public
    @ResponseBody
    Map verifyTestEmail(@PathVariable("key") String key, HttpServletRequest request) throws Exception {

    	Boolean response = userEmailActivationService.verify(key, request);
    	
    	if(response == false) return getNegativeResponseMap("not verified");
    	else return getPositiveResponseMap();

    }

    //------------------- refactor out it not needed ---------------

    public void setUserService(UserServiceImpl value) {

        userService = value;
    }

    public UserService getUserService() {

        return userService;
    }
}
