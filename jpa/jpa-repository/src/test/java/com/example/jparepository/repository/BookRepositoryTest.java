package com.example.jparepository.repository;

import com.example.jparepository.domain.Book;
import com.example.jparepository.domain.Member;
import com.example.jparepository.domain.Publisher;
import com.example.jparepository.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void bookTest(){
        Book book = Book.builder()
                .name("스프링 이해")
                .authorId(1L)
                .build();

        bookRepository.save(book);

        System.out.println(bookRepository.findAll());
    }

    @Test
    @Transactional
    void bookRelationTest(){
        givenBookAndReview();

        Member member = memberRepository.findByEmail("martin@gmail.com").orElseThrow(RuntimeException::new);
        System.out.println("Member : " + member);
        System.out.println("Review : " + member.getReviews());
        System.out.println("Book : " + member.getReviews().get(0).getBook());
        System.out.println("Publisher : " + member.getReviews().get(0).getBook().getPublisher());

    }
    private void givenBookAndReview(){
        givenReview(givenMember(), givenBook(givenPublisher()));
    }
    private Member givenMember(){
        return memberRepository.findByEmail("martin@gmail.com").orElseThrow(RuntimeException::new);
    }
    private Book givenBook(Publisher publisher){
        Book book = Book.builder()
                .name("JPA Learning")
                .publisher(publisher)
                .build();

        return bookRepository.save(book);
    }
    private Publisher givenPublisher(){
        Publisher publisher = new Publisher();
        publisher.setName("DUCKBILL");

        return publisherRepository.save(publisher);
    }
    private void givenReview(Member member, Book book){
        Review review = new Review();
        review.setTitle("내 인생을 바꾼 책");
        review.setContent("너무 너무 재미있고 즐거운 책이었어요.");
        review.setScore(5.0f);
        review.setMember(member);
        review.setBook(book);

        reviewRepository.save(review);
    }
}