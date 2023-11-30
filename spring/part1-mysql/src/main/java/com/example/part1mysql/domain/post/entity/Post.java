package com.example.part1mysql.domain.post.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * author        : duckbill413
 * date          : 2023-04-20
 * description   :
 **/
@Getter
public class Post {
    final private Long id;
    final private Long memberId;
    final private String contents;
    private Long likeCount;
    private Long version; // MEMO: 낙관적 락 버전
    final private LocalDate createdDate;
    final private LocalDateTime createdAt;

    @Builder
    public Post(Long id, Long memberId, String contents, Long likeCount, Long version, LocalDate createdDate, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = Objects.requireNonNull(memberId);
        this.contents = Objects.requireNonNull(contents);
        this.likeCount = likeCount == null ? 0 : likeCount;
        this.version = version == null ? 0 : version;
        this.createdDate = createdDate == null ? LocalDate.now() : createdDate;
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public void incrementCount() {
        likeCount += 1;
    }
}
