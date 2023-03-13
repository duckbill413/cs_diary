package com.example.part1mysql.domain.member.service;

import com.example.part1mysql.domain.member.dto.MemberDto;
import com.example.part1mysql.domain.member.entity.Member;
import com.example.part1mysql.domain.member.repository.MemberJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * author        : duckbill413
 * date          : 2023-03-12
 * description   :
 **/
@Service
@RequiredArgsConstructor
public class MemberReadService {
    private final MemberJdbcRepository memberJdbcRepository;

    public MemberDto getMember(Long id){
        Member member = memberJdbcRepository.findById(id).orElseThrow();
        return toDto(member);
    }

    public MemberDto toDto(Member member){
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
    }
}
