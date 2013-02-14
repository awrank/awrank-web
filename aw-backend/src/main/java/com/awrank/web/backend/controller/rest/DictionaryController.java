package com.awrank.web.backend.controller.rest;

import com.awrank.web.backend.controller.AbstractController;
import com.awrank.web.model.domain.Dictionary;
import com.awrank.web.model.service.DictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public @ResponseBody Dictionary create(@RequestBody Dictionary body) throws Exception {
        return dictionaryService.create(body);
    }

    /**
     * Updates dictionary entry
     */
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json", produces = "application/json")
    public @ResponseBody Dictionary update(@RequestBody Dictionary body) throws Exception {
        return dictionaryService.update(body);
    }

    /**
     * Creates new dictionary entry
     */
    @RequestMapping(method = RequestMethod.DELETE, headers = "Accept=application/json", produces = "application/json")
    public @ResponseBody boolean delete(@RequestParam("id") Long id) throws Exception {
        dictionaryService.delete(id);

        return true;
    }
}
