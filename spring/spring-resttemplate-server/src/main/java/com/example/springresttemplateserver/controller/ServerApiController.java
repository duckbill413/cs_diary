package com.example.springresttemplateserver.controller;

import com.example.springresttemplateserver.dto.Req;
import com.example.springresttemplateserver.dto.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/server")
@RequiredArgsConstructor
public class ServerApiController {

    @GetMapping("/hello")
    public String hello() {
        return "hello server";
    }

    @GetMapping("/hello/user")
    public User helloUser() {
        User user = new User("duckbill", 27);
        return user;
    }

    @GetMapping("/hello/user/param")
    public User helloUserParams(@RequestParam String name,
                                @RequestParam int age) {
        User user = new User(name, age);
        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}")
    public User helloPost(@RequestBody User user,
                          @PathVariable int userId,
                          @PathVariable String userName) {
        log.info("userId: {}", userId);
        log.info("userName: {}", userName);
        log.info("client req : {}", user);
        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}/exchange")
    public User helloPostExchange(@RequestBody User user,
                                  @PathVariable int userId,
                                  @PathVariable String userName,
                                  @RequestHeader("x-auth") String auth) {
        log.info("userId: {}", userId);
        log.info("userName: {}", userName);
        log.info("x-auth: {}", auth);
        log.info("client req : {}", user);
        return user;
    }

    @PostMapping("/user/{userId}/name/{userName}/exchange/generic")
    public Req<User> genericExchange(
//            HttpEntity<String> entity, // response 를 String 형태 그대로 보여준다.
            @RequestBody Req<User> user,
            @PathVariable int userId,
            @PathVariable String userName,
            @RequestHeader("x-auth") String auth) {
//        log.info("req : {}", entity.getBody());
        log.info("userId: {}", userId);
        log.info("userName: {}", userName);
        log.info("x-auth: {}", auth);
        log.info("client req : {}", user);

        Req<User> response = new Req<>();
        response.setHeader(new Req.Header());
        response.setResBody(user.getResBody());
        return response;
    }
}
