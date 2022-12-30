package com.example.jparepository.repository;

import com.example.jparepository.domain.Book;
import com.example.jparepository.domain.BookReviewInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BookReviewInfoRepositoryTest {
    @Autowired
    private BookReviewInfoRepository bookReviewInfoRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void crud() {
        BookReviewInfo bookReviewInfo = new BookReviewInfo();
        bookReviewInfo.setBook(givenBook());
        bookReviewInfo.setAvgReviewScore(4.5f);
        bookReviewInfo.setReviewCount(3);

        bookReviewInfoRepository.save(bookReviewInfo);
        bookReviewInfoRepository.findAll().forEach(System.out::println);
    }

    @Test
    void crud2() {
        givenBookReviewInfo();

        Book result = bookReviewInfoRepository
                .findById(1L)
                .orElseThrow(RuntimeException::new)
                .getBook();

        System.out.println(">>> result1: " + result);

        BookReviewInfo result2 = bookRepository
                .findById(7L)
                .orElseThrow(RuntimeException::new)
                .getBookReviewInfo();
        System.out.println(">>> result2: " + result2);
    }

    private Book givenBook() {
        Book book = new Book();
        book.setName("Jpa");
        book.setAuthorId(1L);
        return bookRepository.save(book);
    }

    private void givenBookReviewInfo() {
        BookReviewInfo bookReviewInfo = new BookReviewInfo();
        bookReviewInfo.setBook(givenBook());
        bookReviewInfo.setAvgReviewScore(4.5f);
        bookReviewInfo.setReviewCount(3);

        bookReviewInfoRepository.save(bookReviewInfo);
    }
}