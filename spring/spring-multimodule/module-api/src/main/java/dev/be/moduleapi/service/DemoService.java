package dev.be.moduleapi.service;

import dev.be.modulecommon.service.CommonDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DemoService {
    private final CommonDemoService commonDemoService;
    public String save() {
        System.out.println(commonDemoService.commonService());
        return "save";
    }

    public String find() {
        return "find";
    }
}
