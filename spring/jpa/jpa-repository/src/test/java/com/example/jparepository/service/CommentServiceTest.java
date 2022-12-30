package com.example.jparepository.service;

import com.example.jparepository.repository.CommentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommentServiceTest {
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

    @Test
    void commentTest(){
        commentService.init();

        commentRepository.findAll().forEach(System.out::println);
    }

    @Test
    void commentUpdateTest(){
        commentService.init();
        commentService.updateSomething();

        commentRepository.findAll().forEach(System.out::println);
    }

    @Test
    void insertSomethingTest(){
        commentService.insertSomething(); // INFO: transation이 있더라도 영속화 되어있지 않기 때문에 DirtyCheck가 발생하지 않는다.
    }
}