package com.example.jparepository.service;

import com.example.jparepository.domain.Book;
import com.example.jparepository.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.RunnableFuture;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void test1(){
        memberService.put();

        System.out.println(">>> " + memberRepository.findByEmail("member@gmail.com"));
    }
    @Test
    void managedTest(){
        memberService.managed();

        System.out.println(">>> " + memberRepository.findByEmail("member@gmail.com").orElseThrow(RuntimeException::new));
    }
    @Test
    void detachedTest(){
        memberService.detached();

        System.out.println(">>> " + memberRepository.findByEmail("member@gmail.com").orElseThrow(RuntimeException::new));
    }
    @Test
    void detachedClearTest(){
        memberService.detached_clear();

        System.out.println(">>> " + memberRepository.findByEmail("member@gmail.com").orElseThrow(RuntimeException::new));
    }

}