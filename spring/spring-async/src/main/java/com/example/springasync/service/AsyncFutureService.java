package com.example.springasync.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.*;

@Slf4j
@Service
public class AsyncFutureService {

    @Async("async-thread")
    public Future<String> runCompletableFuture() {
        log.info("async start running");
        var result = CompletableFuture.completedFuture(doLogic());
        log.info("async end!");
        return result;
    }

    public String doLogic() {
        for (int i = 0; i < 5; i++) {
            try {
                log.info("before " + (i + 1));
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return "future " + LocalDateTime.now();
    }

    public String doDuring() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            sb.append("after ").append(i).append(", ");
        }
        return sb.toString();
    }
}
