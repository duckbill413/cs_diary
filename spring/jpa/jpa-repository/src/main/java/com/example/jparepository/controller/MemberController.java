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
public class MemberController {
    @GetMapping("/hello-world")
    public String helloWorld(){
        return "hello-world";
    }
}
