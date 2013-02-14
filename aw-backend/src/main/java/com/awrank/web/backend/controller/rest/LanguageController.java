package com.awrank.web.backend.controller.rest;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.Language;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.List;

/**
 * Controller exposes basic methods to work with languages through REST.
 */
@Controller
@RequestMapping(value = "/rest/languages")
public class LanguageController extends AbstractController {
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody List<Language> list() {
        return Arrays.asList(Language.values());
    }
}
