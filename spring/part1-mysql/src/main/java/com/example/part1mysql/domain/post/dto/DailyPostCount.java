package com.example.part1mysql.domain.post.dto;

import java.time.LocalDate;

/**
 * author        : duckbill413
 * date          : 2023-04-20
 * description   :
 **/

public record DailyPostCount(
        Long memberId,
        LocalDate date,
        Long postCount
) {
}
