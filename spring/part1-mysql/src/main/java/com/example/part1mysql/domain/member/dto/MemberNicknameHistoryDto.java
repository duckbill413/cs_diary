package com.example.part1mysql.domain.member.dto;

import java.time.LocalDateTime;

/**
 * author        : duckbill413
 * date          : 2023-03-17
 * description   :
 **/

public record MemberNicknameHistoryDto(
        Long id,
        Long memberId,
        String nickname,
        LocalDateTime createdAt
) {
}
