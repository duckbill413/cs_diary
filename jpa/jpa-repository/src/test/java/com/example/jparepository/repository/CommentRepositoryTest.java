package com.example.jparepository.repository;

import com.example.jparepository.domain.Comment;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommentRepositoryTest {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @Transactional
    void commentTest(){
        Comment comment = commentRepository.findById(3L).get();
        comment.setCommentedAt(LocalDateTime.now());
        commentRepository.saveAndFlush(comment);

//        entityManager.clear();
        System.out.println(commentRepository.findById(3L).get());
    }

    @Test
    @Transactional
    void commentTest2(){
        Comment comment = commentRepository.findById(3L).get();
//        comment.setCommentedAt(LocalDateTime.now());
        commentRepository.saveAndFlush(comment);

        entityManager.clear();
        System.out.println(commentRepository.findById(3L).get());
    }

}