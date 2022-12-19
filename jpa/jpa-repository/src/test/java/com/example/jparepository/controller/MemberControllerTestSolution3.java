package com.example.jparepository.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// FIXME: Mvc slice test 는 전체 spring context를 로딩하지 않고 web controller 기능만 로딩하여 발생하는 오류
// FEAT: MocMvc 가 아닌 Spring 전체 context를 로딩하고 mockMvc를 직접 구현하는 방식
//@WebMvcTest
@SpringBootTest
class MemberControllerTestSolution3 {

//    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext woc;

    @BeforeEach
    void before(){
        mockMvc = MockMvcBuilders.webAppContextSetup(woc).build();
    }

    @Test
    void helloWorld() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello-world"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("hello-world"));
    }

}