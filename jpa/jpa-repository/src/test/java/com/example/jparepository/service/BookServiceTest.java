package com.example.jparepository.service;

import com.example.jparepository.domain.Book;
import com.example.jparepository.repository.AuthorRepository;
import com.example.jparepository.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class BookServiceTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookService bookService;

    @Test // MEMO: Transaction 은 메소드가 완료되었을때 수행된다.
    void transactionTest(){
        bookService.putBookAndAuthor();

        System.out.println("books : " + bookRepository.findAll());
        System.out.println("authors : " + authorRepository.findAll());
    }
    @Test // MEMO: Transaction 의 all or nothing 원자성 test
    void transactionErrorTest(){
        try {
            bookService.putBookAndAuthorError();
        }
        // INFO: Exception 에는 Checked 와 UnChecked Exception 이 존재한다. RuntimeException 을 상속하면 UnChecked Exception
        // INFO: 상속하지 않으면 Checked Exception 이며 Checked Exception 은 개발자가 모든 책임을 지게 된다.
        // FEAT: UnCheckedException(RuntimeException) 은 발생시 transaction 에서 rollback 이 수행된다.
        // FEAT: CheckedException 은 발생시 rollback 이 수행되지 않는다. 즉, 개발자가 catch 문에서 직접 rollback 등의 기능을 구현해야 한다.
        catch (RuntimeException e){
            System.out.println(">>> " + e.getMessage());
        }
        System.out.println("books : " + bookRepository.findAll());
        System.out.println("authors : " + authorRepository.findAll());
    }
    @Test
    void isolationTest(){
        Book book = new Book();
        book.setName("JPA 강의");

        bookRepository.save(book);
        bookService.get(1L);

        System.out.println(">>> " + bookRepository.findAll());
    }
}