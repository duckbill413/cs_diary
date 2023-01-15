package com.example.event.storage;

import com.example.event.event.FileEvent;
import com.example.event.event.FileEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class FileService {
    @Autowired
    private FileEventPublisher fileEventPublisher;

    public void fileUpload(Map<String, Object> data) {
        try {
            log.info("파일 복사 완료");
            log.info("DB 파일 메타 정보 저장 완료");
            FileEvent fileEvent = FileEvent.toCompleteEvent(data);
            fileEventPublisher.notifyComplete(fileEvent);
        } catch (Exception e){
            log.error("file upload failed", e);
            FileEvent fileEvent = FileEvent.toErrorEvent(data);
            fileEventPublisher.notifyError(fileEvent);
        }
    }
}
