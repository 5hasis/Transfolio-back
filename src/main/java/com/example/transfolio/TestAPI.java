package com.example.transfolio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAPI {

    @GetMapping("/test")
    public String Test() {

        return "Test Get API1234 Success";

    }

    @PostMapping("/test")
    public String PostTest() {

        return "Test Post API Success TEST";

    }

}
