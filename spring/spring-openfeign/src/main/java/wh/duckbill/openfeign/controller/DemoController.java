package wh.duckbill.openfeign.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import wh.duckbill.openfeign.service.DemoService;

@RestController
@RequiredArgsConstructor
public class DemoController {
    private final DemoService demoService;
    @GetMapping("/get")
    public String getContent() {
        return demoService.get();
    }
}
