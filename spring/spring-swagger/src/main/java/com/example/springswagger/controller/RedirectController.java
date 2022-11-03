package com.example.springswagger.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RedirectController {

    // http://localhost:9090/api/doc 으로 redirect
    @GetMapping("/api/doc")
    public String redirectSwagger(){
        return "redirect:/swagger-ui/";
    }
}
