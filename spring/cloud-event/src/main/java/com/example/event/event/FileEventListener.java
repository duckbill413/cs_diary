package com.example.event.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileEventListener {
    @EventListener // EventListener 완료 구현
    public void onFileEventHandler(FileEvent fileEvent) {
        log.info("file event receive type: {} data: {}", fileEvent.getType(), fileEvent.getData());
    }
}
