package com.example.part1mysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * author        : duckbill413
 * date          : 2023-03-17
 * description   : 멤버의 닉네임 변경 히스토리 저장 테이블
 * 히스토리 데이터는 정규화의 대상이 아니다.
 **/
@Getter
public class MemberNicknameHistory {
    final private Long id;
    final private Long memberId;
    final private String nickname;
    final private LocalDateTime createdAt;
    @Builder
    public MemberNicknameHistory(Long id, Long memberId, String nickname, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.nickname = Objects.requireNonNull(nickname);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }
}
