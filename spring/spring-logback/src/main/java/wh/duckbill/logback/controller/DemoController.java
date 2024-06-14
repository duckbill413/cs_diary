package wh.duckbill.logback.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class DemoController {
    @GetMapping("/demo")
    public String demo() {
        log.trace("trace");
        log.debug("debug");
        log.info("info");
        log.error("error");
        return "demo";
    }
}
