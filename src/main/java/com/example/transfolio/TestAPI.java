package com.example.transfolio;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class TestAPI {

    @GetMapping("/test")
    public String Test() {

        return "/docs/apidocs.html";

    }

    @PostMapping("/test")
    public String PostTest() {

        return "Test Post API Success TEST";

    }

}
