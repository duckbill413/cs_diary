package wh.duckbill.openfeign.feign.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import wh.duckbill.openfeign.feign.interceptor.DemoFeignInterceptor;

@Configuration
public class DemoFeignConfig {
    @Bean
    public DemoFeignInterceptor demoFeignInterceptor() {
        return DemoFeignInterceptor.of();
    }
}
