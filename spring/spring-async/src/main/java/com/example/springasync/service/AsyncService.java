package com.example.springasync.service;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class AsyncService {

    public String hello(){
        for(int i=0; i<10; i++){
            try {
                log.info(String.valueOf(i+1));
                Thread.sleep(2000);
            }
            catch (InterruptedException e){
                e.printStackTrace();
            }
        }

        return "async hello";
    }
    @Async("async-thread")
    public String run() throws ExecutionException, InterruptedException {
        log.info("now running");
        CompletableFuture<String> future = CompletableFuture.completedFuture(hello());
        return future.get();

//        return new AsyncResult(hello()).completable();
    }
}
