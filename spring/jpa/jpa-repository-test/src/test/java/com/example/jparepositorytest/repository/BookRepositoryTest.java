package com.example.jparepositorytest.repository;

import com.example.jparepositorytest.domain.Book;
import com.example.jparepositorytest.domain.Publisher;
import org.assertj.core.util.Lists;
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

    @Test
    void showData(){
        System.out.println("-----------------book----------------------------------");
        bookRepository.findAll().forEach(book -> System.out.println("book : " + book));
        System.out.println("-----------------publisher------------------------------");
        publisherRepository.findAll().forEach(publisher -> System.out.println("publisher : " + publisher));
        System.out.println("-----------------book's publisher-----------------------");
        bookRepository.findAll().forEach(book -> System.out.println("Book Id " + book.getId() + "'s publisher : " + book.getPublisher()));
    }
    @Test
    void cascadeAndOrphanTest(){
        saveData();
        Publisher publisher = publisherRepository.findById(1L).get();
        publisher.getBooks().remove(0);
        publisherRepository.save(publisher);

//        publisherRepository.deleteById(1L);

        showData();
    }

    private void saveData(){
        Book book1 = new Book();
        Book book2 = new Book();
        Book book3 = new Book();
        Book book4 = new Book();
        book1.setName("JPA Book1");
        book2.setName("JPA Book2");
        book3.setName("JPA Book3");
        book4.setName("JPA Book4");

        Publisher publisher1 = new Publisher();
        Publisher publisher2 = new Publisher();
        publisher1.setName("Go Study");
        publisher2.setName("Stop Study");

        publisher1.addBook(book1, book2);
        publisher2.addBook(book3, book4);
        book1.setPublisher(publisher1);
        book2.setPublisher(publisher1);
        book3.setPublisher(publisher2);
        book4.setPublisher(publisher2);

        publisherRepository.saveAll(Lists.newArrayList(publisher1, publisher2));
        bookRepository.saveAll(Lists.newArrayList(book1, book2, book3, book4));
    }
}