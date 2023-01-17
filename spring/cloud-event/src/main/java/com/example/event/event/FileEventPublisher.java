package com.example.event.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
public class FileEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void notifyComplete(FileEvent fileEvent){
        applicationEventPublisher.publishEvent(fileEvent);
    }
    public void notifyError(FileEvent fileEvent){
        applicationEventPublisher.publishEvent(fileEvent);
    }

    @Transactional
    public void fileSet(FileEvent fileEvent){
        // BeforeTransactionEvent
        BeforeFileTransactionEvent beforeFileTransactionEvent = () -> log.info("commit 시작, {}", LocalDateTime.now());
        // AfterTransactionEvent
        AfterFileTransactionEvent afterFileTransactionEvent = new AfterFileTransactionEvent() {
            @Override
            public void complete() {
                log.info("트랜잭션 완료, {}", LocalDateTime.now());
            }

            @Override
            public void callback() {
                log.info("commit 완료, {}", LocalDateTime.now());
            }
        };

        applicationEventPublisher.publishEvent(beforeFileTransactionEvent);
        applicationEventPublisher.publishEvent(afterFileTransactionEvent);
    }
}
