package wh.duckbill.openfeign.controller;

import org.springframework.web.bind.annotation.*;
import wh.duckbill.openfeign.feign.common.dto.BaseRequestInfo;
import wh.duckbill.openfeign.feign.common.dto.BaseResponseInfo;

@RestController
@RequestMapping("/target_server")
public class TargetController {
    @GetMapping("/get")
    public BaseResponseInfo demoGet(@RequestHeader("CustomHeaderName") String header,
                                    @RequestParam("name") String name,
                                    @RequestParam("age") Long age) {
        return BaseResponseInfo.builder()
                .header(header)
                .name(name)
                .age(age)
                .build();
    }

    @PostMapping("/post")
    public BaseResponseInfo demoGet(@RequestHeader("CustomHeaderName") String header,
                                    @RequestBody BaseRequestInfo body) {
        return BaseResponseInfo.builder()
                .header(header)
                .name(body.getName())
                .age(body.getAge())
                .build();
    }
}
