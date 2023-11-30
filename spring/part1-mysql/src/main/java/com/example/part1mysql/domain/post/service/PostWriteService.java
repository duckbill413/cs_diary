package com.example.part1mysql.domain.post.service;

import com.example.part1mysql.domain.post.dto.PostCommand;
import com.example.part1mysql.domain.post.entity.Post;
import com.example.part1mysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * author        : duckbill413
 * date          : 2023-04-20
 * description   :
 **/
@Service
@RequiredArgsConstructor
public class PostWriteService {
    private final PostRepository postRepository;

    public Long create(PostCommand command) {
        var post = Post.builder()
                .memberId(command.memberId())
                .contents(command.contents())
                .build();
        return postRepository.save(post).getId();
    }

    @Transactional
    public void likePost(Long postId) {
        // INFO: 데이터를 조회, 업데이트하는 과정이 같이 있기 때문에 동시성 문제 발생 가능성이 크다.
        // INFO: LOCK을 이용해서 동시성을 제어하는 방법 @Transactional 어노테이션과 MySQL 쓰기락 사용
        Post post = postRepository.findById(postId, true).orElseThrow();
        post.incrementCount();
        postRepository.save(post);
    }

    public void likePostByOptimisticLock(Long postId) {
        Post post = postRepository.findById(postId, false).orElseThrow();
        post.incrementCount();
        postRepository.save(post);
    }
}
