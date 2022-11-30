package com.example.springinterceptor.interceptor;

import com.example.springinterceptor.annotation.Auth;
import com.example.springinterceptor.exception.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // MEMO: Filter 와 마찬가지로 Stream 데이터 이므로 캐싱하여 사용하자

        String url = request.getRequestURI();
        URI uri = UriComponentsBuilder.fromUriString(url)
                        .query(request.getQueryString())
                                .build().toUri();

        log.info("Url request : {}", url);
        boolean hasAnnotation = checkAnnotation(handler, Auth.class);
        log.info("has annotation: {}", hasAnnotation);

        // 나의 서버는 모두 public 으로 동작을 하는데
        // 단, Auth 권한을 가진 요청에 대해서는 세션, 쿠키 등을 검사
        if (hasAnnotation){
            String query = uri.getQuery();
            log.info("query: {}", query);
            if (query.equals("name=duckbill")){
                return true;
            }
            else {
                throw new AuthException();
            }
        }

        return true; // MEMO: return false 이면 동작하지 않는다.
    }

    private boolean checkAnnotation(Object handler, Class clazz){
        // resource javascript, html...
        if (handler instanceof ResourceHttpRequestHandler){
            return true;
        }

        // annotation check
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(clazz) != null || handlerMethod.getBeanType().getAnnotation(clazz) != null){
            // Auth annotation 이 있을때는 True
            return true;
        }

        return false;
    }
}
