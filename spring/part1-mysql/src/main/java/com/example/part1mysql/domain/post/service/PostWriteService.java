package com.example.part1mysql.domain.post.service;

import com.example.part1mysql.domain.post.dto.PostCommand;
import com.example.part1mysql.domain.post.entity.Post;
import com.example.part1mysql.domain.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * author        : duckbill413
 * date          : 2023-04-20
 * description   :
 **/
@Service
@RequiredArgsConstructor
public class PostWriteService {
    private final PostRepository postRepository;

    public Long create(PostCommand command){
        var post = Post.builder()
                .memberId(command.memberId())
                .contents(command.contents())
                .build();
        return postRepository.save(post).getId();
    }
}
