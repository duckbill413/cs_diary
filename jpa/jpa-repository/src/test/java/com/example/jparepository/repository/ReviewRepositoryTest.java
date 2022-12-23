package com.example.jparepository.repository;

import com.example.jparepository.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ReviewRepositoryTest {
    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    void reviewTest1(){
        List<Review> reviews = reviewRepository.findAllByFetchJoin();

//        System.out.println("전체를 가져왔습니다.");
//
//        System.out.println(reviews.get(0).getComments());
//
//        System.out.println("첫번째 리뷰의 코멘트들을 가져왔습니다.");
//
//        System.out.println(reviews.get(1).getComments());
//
//        System.out.println("두번쨰 리뷰의 코멘트들을 가져왔습니다.");

        reviews.forEach(System.out::println);
    }
    @Test
    @Transactional
    void reviewTest2(){
        List<Review> reviews = reviewRepository.findAllByEntityGraph();
        reviews.forEach(System.out::println);
    }

    @Test
    @Transactional
    void reviewTest3(){
        List<Review> reviews = reviewRepository.findAll();
        reviews.forEach(System.out::println);
    }
}