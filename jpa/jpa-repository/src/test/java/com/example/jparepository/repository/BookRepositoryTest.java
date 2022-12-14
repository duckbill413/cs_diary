package com.example.jparepository.repository;

import com.example.jparepository.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    void bookTest(){
        Book book = Book.builder()
                .name("스프링 이해")
                .authorId(1L)
                .publisherId(1L)
                .build();

        bookRepository.save(book);

        System.out.println(bookRepository.findAll());
    }

}