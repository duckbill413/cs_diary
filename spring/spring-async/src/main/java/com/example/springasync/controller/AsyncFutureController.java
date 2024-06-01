package com.example.springasync.controller;

import com.example.springasync.service.AsyncFutureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AsyncFutureController {

    private final AsyncFutureService asyncFutureService;

    @GetMapping("/hello")
    public String hello() throws ExecutionException, InterruptedException {
        CompletableFuture<String> hello = asyncFutureService.run();
        log.info("completable future init");
        return hello.get();
    }
}
