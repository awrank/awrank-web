package com.awrank.web.backend.controller.auth;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/rest")
public class TestRestController {
    public class TestResource implements Serializable {
        public String var1;

        @JsonIgnore
        public String var2;

        public TestResource() {
        }

        public TestResource(String var1, String var2) {
            this.var1 = var1;
            this.var2 = var2;
        }
    }


    @RequestMapping(value="/search", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json")
    public @ResponseBody List<TestResource> search(@RequestParam(value = "query", required = true) String query) {
        List<TestResource> result =  new ArrayList<TestResource>();

        result.add(new TestResource("1", "hidden"));
        result.add(new TestResource("2", "hidden"));

        return result;
    }
}

