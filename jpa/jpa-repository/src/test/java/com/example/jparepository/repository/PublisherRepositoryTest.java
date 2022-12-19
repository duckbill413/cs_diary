package com.example.jparepository.repository;

import com.example.jparepository.domain.Book;
import com.example.jparepository.domain.Publisher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PublisherRepositoryTest {
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookRepository bookRepository;

    @Test
    @Transactional
    void test1(){
        givenBook();
        Book book = bookRepository.findByName("JPA 탐험").orElseThrow(RuntimeException::new);
        System.out.println("Book : " + book);
        System.out.println("Book to Publisher : " + book.getPublisher());
//        System.out.println("Publisher : " + publisher.getBooks());
    }

    private Publisher givenPublisher(){
        Publisher publisher = new Publisher();
        publisher.setName("neo");
        return publisherRepository.save(publisher);
    }

    private void givenBook(){
        Book book = new Book();
        book.setName("JPA 탐험");
        book.setCategory("교재");
        book.setPublisher(givenPublisher());

        bookRepository.save(book);
    }

}