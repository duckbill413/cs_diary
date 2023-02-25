package com.example.part1mysql.controller;

import com.example.part1mysql.domain.member.dto.RegisterMemberCommand;
import com.example.part1mysql.domain.member.entity.Member;
import com.example.part1mysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * author        : duckbill413
 * date          : 2023-02-24
 * description   :
 **/
@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class MemberController {
    final private MemberWriteService memberWriteService;
    @PostMapping("/members")
    public Member register(@RequestBody RegisterMemberCommand command){
        return memberWriteService.create(command);
    }
}
