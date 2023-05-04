package com.example.part1mysql.application.controller;

import com.example.part1mysql.domain.post.dto.DailyPostCount;
import com.example.part1mysql.domain.post.dto.DailyPostCountRequest;
import com.example.part1mysql.domain.post.dto.PostCommand;
import com.example.part1mysql.domain.post.entity.Post;
import com.example.part1mysql.domain.post.service.PostReadService;
import com.example.part1mysql.domain.post.service.PostWriteService;
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

    @PostMapping("")
    public Long create(PostCommand command){
        // FIXME: 회원 인증 및 회원 검증 구현
        return postWriteService.create(command);
    }
    @GetMapping("/daily-post-counts")
    public List<DailyPostCount> getDailyPostCounts(@RequestBody DailyPostCountRequest request){
        return postReadService.getDailyPostCount(request);
    }
    @GetMapping("/members/{memberId}")
    public Page<Post> getPosts(
            @PathVariable Long memberId,
            Pageable pageable
    ){
        return postReadService.getPosts(memberId, pageable);
    }
}
