package com.polarbookshop.catalogservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "polar") // 'polar'로 시작하는 속성에 대한 소스임을 표시
public class PolarProperties {
    private String greeting; // 사용자 정의 속성인 polar.greeting (prefix + fieldName) 속성이 문자열로 인식

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String greeting) {
        this.greeting = greeting;
    }
}
