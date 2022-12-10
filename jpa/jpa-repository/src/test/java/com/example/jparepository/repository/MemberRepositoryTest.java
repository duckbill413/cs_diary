package com.example.jparepository.repository;

import com.example.jparepository.domain.Member;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void crud(){ // create, read, update, delete

        System.out.println(">>> name 을 기준으로 내림차순 출력");
        List<Member> members1 = memberRepository.findAll(Sort.by(Sort.Direction.DESC, "name"));
        members1.forEach(System.out::println);

        System.out.println(">>> ID 가 1, 3, 5번인 Member 출력");
        List<Member> members2 = memberRepository.findAllById(Lists.newArrayList(1L, 3L, 5L));
        members2.forEach(System.out::println);

        System.out.println(">>> 멤버 삽입");
        Member member1 = new Member("duckbill", "duckbill@gmail.com");
        Member member2 = new Member("pypy", "pypy@gmail.com");
        memberRepository.saveAll(Lists.newArrayList(member1, member2));
        memberRepository.findAll().forEach(System.out::println);
    }
}