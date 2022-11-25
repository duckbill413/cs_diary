package com.example.springresponse.controller;

import com.example.springresponse.dto.User;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PageController {

    @RequestMapping("/main")
    public String main(){
        return "main.html";
    }

    @ResponseBody
    @GetMapping("/user")
    public User user(){
        val user = new User();
        user.setName("duckbill");
        user.setAddress("liver");
        return user;
    }
}
