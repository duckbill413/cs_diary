package com.example.jparepository.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// FIXME: Mvc slice test 는 전체 spring context를 로딩하지 않고 web controller 기능만 로딩하여 발생하는 오류
// FEAT: configuration -> JpaCongifuration 을 생성하고 @EnableJpaAuditing 을 대신 붙이면 해결됨
// FEAT: 보편적인 해결책으로 사용된다.
@WebMvcTest
class MemberControllerTestSolution2 {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void helloWorld() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello-world"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello-world"));
    }

}