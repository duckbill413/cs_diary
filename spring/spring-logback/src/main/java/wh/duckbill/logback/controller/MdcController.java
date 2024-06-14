package wh.duckbill.logback.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MdcController {

    /**
     * MDC란?
     * 멀티스레드 환경에서 로그를 남길 때 사용하는 개념
     * 스레드 마다 고유한 log 값을 가지고 있고 그걸 로그 백에 전달해 주기 위해서 사용하는 개념
     * 스레드 별로 mdc에 들어가 있는 값을 관리
     * MDC를 clear하지 않고 다른 요청이 동일한 스레드를 참조한다면 이 값들이 정리가 되어 있지 않은 경우 다음 스레드가 get하게 된다.
     * 따라서, clear를 사용하는 것을 잊지 말아야 한다.
     * MDC는 로그에서 해당 값에 저장되어 있는 값을 동적으로 가져와서 출력하기 위해 사용한다.
     */
    @GetMapping("/mdc")
    public String mdc() {
        MDC.put("job", "dev"); // key, value
        log.trace("log --> trace");
        log.debug("log --> debug");
        log.info("log --> info");
        log.warn("log --> warn");
        log.error("log --> error");
        MDC.clear();

        return "mdc";
    }
}
