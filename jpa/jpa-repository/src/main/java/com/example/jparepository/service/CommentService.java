package com.example.jparepository.service;

import com.example.jparepository.domain.Comment;
import com.example.jparepository.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    @Transactional
    public void init(){
        for (int i=0; i<10; i++){
            Comment comment = new Comment();
            comment.setComment("최고입니다.");

            commentRepository.save(comment);
        }
    }

    @Transactional
    public void updateSomething(){
        List<Comment> comments = commentRepository.findAll();

        for (Comment comment: comments){
            comment.setComment("별로네요..");

//            commentRepository.save(comment); // FEAT: save 문이 없어도 update 가 일어난다.
            // MEMO: 영속성 관리중일어난 일에대하여 별도의 save 메서드가 없더라도 반영해주는 것
        }
    }

    @Transactional(readOnly = true)
    public void insertSomething(){
        Comment comment = new Comment();
        comment.setComment("이건 뭐죠?");
    }
}
