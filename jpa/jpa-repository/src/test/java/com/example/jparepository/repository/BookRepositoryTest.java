package com.example.jparepository.repository;

import com.example.jparepository.domain.Book;
import com.example.jparepository.domain.Member;
import com.example.jparepository.domain.Publisher;
import com.example.jparepository.domain.Review;
import org.aspectj.weaver.ISourceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

import javax.transaction.Transactional;

import java.time.LocalDateTime;

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

    @Test // INFO: Cascade PERSIST
    void bookCascadeTest(){
        Book book = new Book();
        book.setName("JPA 초격차 패키지");

        Publisher publisher = new Publisher();
        publisher.setName("공부하자");

        book.setPublisher(publisher);
        bookRepository.save(book); // book entity 를 업데이트 했을때 영속성 전이로 인해 publisher 도 같이 update 됨

        System.out.println("book : " + bookRepository.findAll());
        System.out.println("publishers : " + publisherRepository.findAll());

        Book findBook = bookRepository.findById(1L).get();
        findBook.getPublisher().setName("공부싫어");
        bookRepository.save(findBook); // update 는 MERGE 에 해당하므로 PERSIST 에서는 영속성 전이가 일어나지 않는다. 따라서 publisher 가 업데이트 되지 않는다.

        System.out.println("publishers : " + publisherRepository.findAll());
    }
    @Test // INFO: Orphan Removal (고아제거속성)
    void bookCascadeTest2(){
        Book book = new Book();
        book.setName("JPA 초격차 패키지");

        Publisher publisher = new Publisher();
        publisher.setName("공부하자");

        book.setPublisher(publisher);
        bookRepository.save(book); // book entity 를 업데이트 했을때 영속성 전이로 인해 publisher 도 같이 update 됨

        Book findBook = bookRepository.findById(1L).get();
        findBook.getPublisher().setName("공부싫어");
        bookRepository.save(findBook); // update 는 MERGE 에 해당하므로 PERSIST 에서는 영속성 전이가 일어나지 않는다. 따라서 publisher 가 업데이트 되지 않는다.

//        Book findBook2 = bookRepository.findById(1L).get();
//        bookRepository.delete(findBook2);

        Book book3 = bookRepository.findById(1L).get();
        book3.setPublisher(null);
        bookRepository.save(book3);

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publishers : " + publisherRepository.findAll());
        System.out.println("book3-publisher : " + bookRepository.findById(1L));
    }

    @Test
    void bookRemoveCascadeTest(){
        bookRepository.deleteById(1L);
        System.out.println("books : " + bookRepository.findAll());
        System.out.println("publisher : " + publisherRepository.findAll());

        bookRepository.findAll().forEach(book -> System.out.println(book.getPublisher()));
    }

    @Test
    void softDelete(){
        bookRepository.findAll().forEach(System.out::println);
    }

    @Test
    void tt(){
        bookRepository.deleteById(1L);
        ttt();
    }

    @Test
    void ttt(){
        System.out.println("books-------------------------------------");
        bookRepository.findAll().forEach(book -> System.out.println("book : " + book));
        System.out.println("publishers--------------------------------");
        publisherRepository.findAll().forEach(publisher -> System.out.println("publisher : " + publisher));
        System.out.println("bp----------------------------------------");
        bookRepository.findAll().forEach(book -> System.out.println("Book Id " + book.getId() + "'s publisher : " + book.getPublisher()));
    }

    @Test
    void findByNameRecentlyTest(){
        System.out.println("findByNameRecently : " +
                bookRepository.findByNameRecently("JPA Master",
                        LocalDateTime.now().minusDays(1L),
                        LocalDateTime.now().minusDays(1L)));

    }

    @Test
    void findByWant(){
        bookRepository.findBookNameAndCategory().forEach(bookNameAndCategory -> {
            System.out.println(bookNameAndCategory.getName() + " : " + bookNameAndCategory.getCategory());
        });
        bookRepository.findBookNameAndCategoryByPage(PageRequest.of(1, 1)).forEach(
                bookNameAndCategoryClass -> System.out.println(bookNameAndCategoryClass.getName() + " : " + bookNameAndCategoryClass.getCategory())
        );
        bookRepository.findBookNameAndCategoryByPage(PageRequest.of(0, 1)).forEach(
                bookNameAndCategoryClass -> System.out.println(bookNameAndCategoryClass.getName() + " : " + bookNameAndCategoryClass.getCategory())
        );
    }
}