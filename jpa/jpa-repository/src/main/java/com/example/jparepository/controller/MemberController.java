package com.example.jparepository.controller;

import com.example.jparepository.domain.Member;
import com.example.jparepository.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;

    @GetMapping("/all")
    public ResponseEntity<List<Member>> getAllUser() {
        memberRepository.save(new Member(1L, "steve", "steve@gmail.com",
                LocalDateTime.now(), LocalDateTime.now()));
        memberRepository.save(new Member(2L, "duckbill", "duckbill@gmail.com",
                LocalDateTime.now(), LocalDateTime.now()));
        List<Member> users = memberRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
