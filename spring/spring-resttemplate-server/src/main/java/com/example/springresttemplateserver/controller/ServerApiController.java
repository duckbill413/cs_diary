package com.example.springresttemplateserver.controller;

import com.example.springresttemplateserver.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/server")
@RequiredArgsConstructor
public class ServerApiController {

    @GetMapping("/hello")
    public String hello(){
        return "hello server";
    }

    @GetMapping("/hello/user")
    public User helloUser(){
        User user = new User("duckbill", 27);
        return user;
    }

    @GetMapping("/hello/user/param")
    public User helloUserParams(@RequestParam String name,
                                @RequestParam int age){
        User user = new User(name, age);
        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}")
    public User helloPost(@RequestBody User user,
                          @PathVariable int userId,
                          @PathVariable String userName){
        log.info("userId: {}", userId);
        log.info("userName: {}", userName);
        log.info("client req : {}", user);
        return user;
    }

}
