package com.example.part1mysql.domain.member.service;

import com.example.part1mysql.domain.member.dto.MemberDto;
import com.example.part1mysql.domain.member.dto.MemberNicknameHistoryDto;
import com.example.part1mysql.domain.member.entity.Member;
import com.example.part1mysql.domain.member.entity.MemberNicknameHistory;
import com.example.part1mysql.domain.member.repository.MemberJdbcRepository;
import com.example.part1mysql.domain.member.repository.MemberNicknameJdbcRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author        : duckbill413
 * date          : 2023-03-12
 * description   :
 **/
@Service
@RequiredArgsConstructor
public class MemberReadService {
    private final MemberJdbcRepository memberJdbcRepository;
    private final MemberNicknameJdbcRepository memberNicknameJdbcRepository;

    public MemberDto getMember(Long id){
        Member member = memberJdbcRepository.findById(id).orElseThrow();
        return toDto(member);
    }

    public MemberDto toDto(Member member){
        return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirthday());
    }

    public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId){
        return memberNicknameJdbcRepository.findAllByMemberId(memberId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private MemberNicknameHistoryDto toDto(MemberNicknameHistory history){
        return new MemberNicknameHistoryDto(
                history.getId(),
                history.getMemberId(),
                history.getNickname(),
                history.getCreatedAt()
        );
    }
}
