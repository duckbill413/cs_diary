package wh.duckbill.netflix.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wh.duckbill.netflix.interceptor.RequestedByInterceptor;

@Component
@EnableWebMvc
@RequiredArgsConstructor
public class RequestedByMvcConfigurer implements WebMvcConfigurer {
    private final RequestedByInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addWebRequestInterceptor(interceptor);
    }
}
