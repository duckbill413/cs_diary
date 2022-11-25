package com.example.springresponse.controller;

import com.example.springresponse.dto.User;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/text")
    public String text(@RequestParam String account){
        return account;
    }
    // MEMO: req -> object mapper -> object -> method -> object -> object mapper -> json -> response
    @PostMapping("/json")
    public User json(@RequestBody User user){
        return user; // 200 ok
    }
    // INFO: 201 RETURN <= ResponseEntity
    @PutMapping("/put")
    public ResponseEntity<User> get(@RequestBody User user){
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
