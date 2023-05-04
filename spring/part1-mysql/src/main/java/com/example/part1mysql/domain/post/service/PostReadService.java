package com.example.part1mysql.domain.post.service;

import com.example.part1mysql.domain.post.dto.DailyPostCount;
import com.example.part1mysql.domain.post.dto.DailyPostCountRequest;
import com.example.part1mysql.domain.post.entity.Post;
import com.example.part1mysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author        : duckbill413
 * date          : 2023-04-20
 * description   :
 **/
@Service
@RequiredArgsConstructor
public class PostReadService {
    private final PostRepository postRepository;

    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        /*
        반환 값 -> 리스트 [작성일자, 작성회원, 작성 게시물 수]
        select *
        from Post
        where memberId = :memberId and createdDate between firstDate and lastDate
        group by createdDate memberId
         */
        return postRepository.groupByCreatedDate(request);
    }

    public Page<Post> getPosts(Long memberId, Pageable pageable) {
        return postRepository.findAllMemberId(memberId, pageable);
    }

    public Page<Post> getPasts()
    Long member
}
