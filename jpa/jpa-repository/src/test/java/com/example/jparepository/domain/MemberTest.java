package com.example.jparepository.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

class MemberTest {

    @Test
    void test(){
        Member member = new Member();
        member.setName("steve");
        member.setEmail("steve@gmail.com");
        member.setCreatedAt(LocalDateTime.now());
        member.setUpdatedAt(LocalDateTime.now());

        Member member1 = new Member(null, "martin", "martin@gmail.com", LocalDateTime.now(), LocalDateTime.now());
        Member member2 = new Member("james", "james@gmail.com");

        Member member3 = Member.builder()
                .name("john")
                .email("john@yahoo.ac.kr")
                .build();

        System.out.println(member);
    }
}