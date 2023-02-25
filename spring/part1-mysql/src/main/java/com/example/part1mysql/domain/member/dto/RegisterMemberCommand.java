package com.example.part1mysql.domain.member.dto;

import java.time.LocalDate;

/**
 * author        : duckbill413
 * date          : 2023-02-23
 * description   :
 **/

public record RegisterMemberCommand(
        String email,
        String nickname,
        LocalDate birthDay
) {
}
