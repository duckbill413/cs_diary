package wh.duckbill.openfeign.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wh.duckbill.openfeign.feign.logger.FeignCustomLogger;

/**
 * Feign 과 관련된 설정을 위한 클래스
 */
@Configuration
public class FeignConfig {

    /**
     * Feign 에서 logging할 level 지정
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.HEADERS;
    }

    /**
     * Feign의 Global Logger 클래스 등록
     */
    @Bean
    public Logger feignLogger() {
        return FeignCustomLogger.of();
    }
}
