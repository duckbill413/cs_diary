package com.example.part1mysql.domain.member.dto;

import java.time.LocalDate;

/**
 * author        : duckbill413
 * date          : 2023-03-12
 * description   :
 **/

public record MemberDto(
        Long id,
        String email,
        String nickname,
        LocalDate birthday
) {
}
