package com.example.part1mysql.domain.member.service;

import com.example.part1mysql.domain.member.dto.RegisterMemberCommand;
import com.example.part1mysql.domain.member.entity.Member;
import com.example.part1mysql.domain.member.repository.MemberJdbcRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

/**
 * author        : duckbill413
 * date          : 2023-02-23
 * description   :
 **/
@Service
@RequiredArgsConstructor
public class MemberWriteService {
    final private MemberJdbcRepository memberJdbcRepository;
    public Member create(RegisterMemberCommand command) {
        /*
            목표 - 회원정보(이메일, 닉네임, 생년월일)을 등록한다.
                - 닉네임은 10자를 넘길 수 없다.
            파라미터 - memberRegisterCommand

            val member = Member.of(memberRegisterCommand)
            memberRepository.save(member)
         */
        var member = Member.builder()
                .nickname(command.nickname())
                .email(command.email())
                .birthDay(command.birthDay())
                .build();
        return memberJdbcRepository.save(member);
    }
}
