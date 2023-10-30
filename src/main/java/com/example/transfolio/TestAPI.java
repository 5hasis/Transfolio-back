package com.example.transfolio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestAPI {

    @GetMapping("/test")
    public String Test() {

        return "Test API Success";

    }

}
