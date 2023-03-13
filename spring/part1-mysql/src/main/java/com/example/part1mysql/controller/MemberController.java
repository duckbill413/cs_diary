package com.example.part1mysql.controller;

import com.example.part1mysql.domain.member.dto.MemberDto;
import com.example.part1mysql.domain.member.dto.RegisterMemberCommand;
import com.example.part1mysql.domain.member.service.MemberReadService;
import com.example.part1mysql.domain.member.service.MemberWriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * author        : duckbill413
 * date          : 2023-02-24
 * description   :
 **/
@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    final private MemberWriteService memberWriteService;
    private final MemberReadService memberReadService;
    @PostMapping
    public MemberDto register(@RequestBody RegisterMemberCommand command){
        var member = memberWriteService.register(command);
        return memberReadService.toDto(member);
    }

    @GetMapping("/{id}")
    public MemberDto read(@PathVariable Long id){
        return memberReadService.getMember(id);
    }
    @PostMapping("/{id}")
    public MemberDto changeNickname(@PathVariable Long id,
                                    @RequestBody String nickname){
        memberWriteService.changeNickname(id, nickname);
        return memberReadService.getMember(id);
    }
}
