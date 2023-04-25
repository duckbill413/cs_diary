package com.example.part1mysql.domain.post.dto;

/**
 * author        : duckbill413
 * date          : 2023-04-20
 * description   :
 **/

public record PostCommand(
        Long memberId,
        String contents
) {
}
