package com.example.springasync.controller;

import com.example.springasync.service.AsyncFutureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AsyncFutureController {

    private final AsyncFutureService asyncFutureService;

    @GetMapping("/future")
    public String completableFutureExample() throws ExecutionException, InterruptedException {
        Future<String> hello = asyncFutureService.runCompletableFuture();
        log.info("do start!");
        log.info(asyncFutureService.doDuring());
        log.info("do end!");

        var result = hello.get();
        log.info(result);
        return result;
    }
}
