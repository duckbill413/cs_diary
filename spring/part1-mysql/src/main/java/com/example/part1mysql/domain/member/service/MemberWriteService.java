package com.example.part1mysql.domain.member.service;

import com.example.part1mysql.domain.member.dto.RegisterMemberCommand;
import com.example.part1mysql.domain.member.entity.Member;
import com.example.part1mysql.domain.member.entity.MemberNicknameHistory;
import com.example.part1mysql.domain.member.repository.MemberJdbcRepository;
import com.example.part1mysql.domain.member.repository.MemberNicknameHistoryJdbcRepository;
import lombok.RequiredArgsConstructor;
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
    final private MemberNicknameHistoryJdbcRepository memberNicknameHistoryJdbcRepository;

    public Member register(RegisterMemberCommand command) {
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
                .birthday(command.birthDay())
                .build();

        var savedMember = memberJdbcRepository.save(member);
        saveNicknameHistory(savedMember);
        return savedMember;
    }

    public void changeNickname(Long memberId, String nickname) {
        /*
        1. 회원 이름을 변경
        2. 변경 내역을 저장
         */
        Member member = memberJdbcRepository.findById(memberId).orElseThrow();
        member.changeNickname(nickname);
        memberJdbcRepository.save(member);
        // TODO: 변경내역 히스토리 저장
        saveNicknameHistory(member);
    }

    private void saveNicknameHistory(Member member) {
        var history = MemberNicknameHistory.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .build();
        memberNicknameHistoryJdbcRepository.save(history);
    }
}
