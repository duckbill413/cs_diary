package com.example.event.storage;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class FileController {
    @Autowired
    private FileService fileService;
    @GetMapping("/upload/image")
    public ResponseEntity fileUpload() {
        Map<String, Object> data = new HashMap<>();
        data.put("userId", "홍길동");
        data.put("type", "webp");
        data.put("fileSize", 5);

        fileService.fileUpload(data);
        return ResponseEntity.ok("success");
    }

    @GetMapping("/set/image")
    public ResponseEntity fileSet(){
        Map<String, Object> data = new HashMap<>();
        data.put("userId", "홍길동");
        data.put("type", "webp");
        data.put("fileSize", 5);

        fileService.fileSet(data);
        return ResponseEntity.ok("success");
    }
}
