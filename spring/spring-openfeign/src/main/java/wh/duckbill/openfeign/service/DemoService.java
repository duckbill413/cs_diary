package wh.duckbill.openfeign.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import wh.duckbill.openfeign.feign.client.DemoFeignClient;
import wh.duckbill.openfeign.feign.common.dto.BaseRequestInfo;
import wh.duckbill.openfeign.feign.common.dto.BaseResponseInfo;

@Service
@RequiredArgsConstructor
public class DemoService {
    private final DemoFeignClient demoFeignClient;

    public String get() {
        ResponseEntity<BaseResponseInfo> response = demoFeignClient.callGet("CustomHeader", "CustomName", 1L);

        System.out.println("FROM TARGET SERVER");
        System.out.println("Name: " + response.getBody().getName());
        System.out.println("Age: " + response.getBody().getAge());
        System.out.println("Header: " + response.getBody().getHeader());
        return "get";
    }

    public String post() {
        ResponseEntity<BaseResponseInfo> response = demoFeignClient.callPost("CustomHeader", BaseRequestInfo.builder()
                .name("duckbill")
                .age(29L)
                .build());

        System.out.println("FROM TARGET SERVER");
        System.out.println("Name: " + response.getBody().getName());
        System.out.println("Age: " + response.getBody().getAge());
        System.out.println("Header: " + response.getBody().getHeader());
        return "post";
    }

    public String errorDecoder() {
        demoFeignClient.callErrorDecoder();
        return "error";
    }
}
