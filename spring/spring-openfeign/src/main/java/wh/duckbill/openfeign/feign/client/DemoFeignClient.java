package wh.duckbill.openfeign.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import wh.duckbill.openfeign.feign.common.dto.BaseRequestInfo;
import wh.duckbill.openfeign.feign.common.dto.BaseResponseInfo;
import wh.duckbill.openfeign.feign.config.DemoFeignConfig;

@FeignClient(
        name = "demo-client", // feign client 의 고유 이름
        url = "${feign.url.prefix}", // 요청을 전송할 서버의 url
        configuration = {DemoFeignConfig.class} // 어떤 설정을 적용할 것인지 설정
)
public interface DemoFeignClient {

    @GetMapping("/get")
    ResponseEntity<BaseResponseInfo> callGet(@RequestHeader("CustomHeaderName") String customHeader,
                                             @RequestParam("name") String name,
                                             @RequestParam("age") Long age);

    @PostMapping("/post")
    ResponseEntity<BaseResponseInfo> callPost(@RequestHeader("CustomHeaderName") String customHeader,
                                              @RequestBody BaseRequestInfo baseRequestInfo);
}
