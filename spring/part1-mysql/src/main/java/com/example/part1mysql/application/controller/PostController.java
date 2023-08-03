package com.example.part1mysql.application.controller;

import com.example.part1mysql.application.usecase.GetTimelinePostUsecase;
import com.example.part1mysql.domain.post.dto.DailyPostCount;
import com.example.part1mysql.domain.post.dto.DailyPostCountRequest;
import com.example.part1mysql.domain.post.dto.PostCommand;
import com.example.part1mysql.domain.post.entity.Post;
import com.example.part1mysql.domain.post.service.PostReadService;
import com.example.part1mysql.domain.post.service.PostWriteService;
import com.example.part1mysql.domain.util.CursorRequest;
import com.example.part1mysql.domain.util.PageCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * author        : duckbill413
 * date          : 2023-04-20
 * description   :
 **/
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {
    private final PostWriteService postWriteService;
    private final PostReadService postReadService;
    private final GetTimelinePostUsecase getTimelinePostUsecase;

    @PostMapping("")
    public Long create(PostCommand command) {
        // FIXME: 회원 인증 및 회원 검증 구현
        return postWriteService.create(command);
    }

    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(@RequestBody DailyPostCountRequest request) {
        return postReadService.getDailyPostCount(request);
    }

    /**
     * Offset 페이징 기반 포스트 조회
     * Offset 기반 페이징의 경우 조회 이후 데이터가 추가되면 다음 페이지에서도 중복해서 데이터가 보일 수 있다.
     *
     * @param memberId 포스트를 검색하고자 하는 회원
     * @param pageable 페이징 처리를 위한 파라미터 ex) page, size
     * @return
     */
    @GetMapping("/members/{memberId}")
    public Page<Post> getPosts(
            @PathVariable Long memberId,
            Pageable pageable
    ) {
        return postReadService.getPosts(memberId, pageable);
    }

    /**
     * Cursor 페이징 기반 포스트 조회
     * Cursor 기반의 경우 검색하고자 하는 컬럼을 기반으로 정렬이 이루어져야 한다.
     * Offset 기반과 달리 id 정렬을 이용하여 조회하므로 데이터가 중간에 추가 되더라도 중복해서 데이터가 보이지 않는다.
     *
     * @param memberId      포스트를 검색하고자 하는 회원
     * @param cursorRequest key: key 미만의 데이터를 조회, size: 한 페이지에 조회할 데이터의 개수
     */
    @GetMapping("/members/{memberId}/by-cursor")
    public PageCursor<Post> getPostsByCursor(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
        return postReadService.getPosts(memberId, cursorRequest);
    }

    @GetMapping("/members/{memberId}/timeline")
    public PageCursor<Post> getTimeline(
            @PathVariable Long memberId,
            CursorRequest cursorRequest
    ) {
        return getTimelinePostUsecase.execute(memberId, cursorRequest);
    }
}
