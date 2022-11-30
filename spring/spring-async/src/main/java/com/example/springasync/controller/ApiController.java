package com.example.springasync.controller;

import com.example.springasync.service.AsyncService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final AsyncService asyncService;

    @GetMapping("/hello")
    public String hello() throws ExecutionException, InterruptedException {
        String hello = asyncService.run();
        log.info("completable future init");
        return hello;
    }
}
