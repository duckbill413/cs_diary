package com.example.springfilter.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
//@Component
@WebFilter(urlPatterns = "/api/user/**")
public class GlobalFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 전처리
        ContentCachingRequestWrapper contentCachingRequestWrapper =
                new ContentCachingRequestWrapper((HttpServletRequest) request);
        ContentCachingResponseWrapper contentCachingResponseWrapper =
                new ContentCachingResponseWrapper((HttpServletResponse) response);

        /* FIXME request 가 stream buffer 로 구성되어 한번 읽으면 그 담에 값이 읽히지 않음 -> ContentCachingRequestWrapper 로 구현
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
         */

        // MEMO: 생성되었을때는 읽지 않고 길이만 초기화 doFilter 로 내부 Spring 에 들어가야 메소드가 실행 되어서 request 내용이 ByteArray 에 담겨 읽을 수 있게 된다.
        chain.doFilter(request, response);

        // MEMO: doFilter 이후에 실행 되어야 한다.
        String url = contentCachingRequestWrapper.getRequestURI();

        // 후처리
        String reqContent = new String(contentCachingRequestWrapper.getContentAsByteArray());
        log.info("response status: {}, responseBody : {}", url, reqContent);

        String resContent = new String(contentCachingResponseWrapper.getContentAsByteArray());

        int httpStatus = contentCachingResponseWrapper.getStatus();
        log.info("response status: {}, responseBody : {}", httpStatus, resContent);

        // MEMO: 다시 한번 Body 내용을 채워 넣는다.
        contentCachingResponseWrapper.copyBodyToResponse();
    }
}
