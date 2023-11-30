package com.example.part1mysql.domain.post.service;

import com.example.part1mysql.domain.member.dto.MemberDto;
import com.example.part1mysql.domain.post.entity.Post;
import com.example.part1mysql.domain.post.entity.PostLike;
import com.example.part1mysql.domain.post.repository.PostLikeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class PostLikeWriteService {
    private final PostLikeRepository postLikeRepository;
    public Long create(Post post, MemberDto memberDto) {
        PostLike postLike = PostLike.builder()
                .postId(post.getId())
                .memberId(memberDto.id())
                .build();

        return postLikeRepository.save(postLike).getPostId();
    }
}
