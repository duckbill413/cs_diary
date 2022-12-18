package com.example.jparepository.service;

import com.example.jparepository.domain.Author;
import com.example.jparepository.domain.Book;
import com.example.jparepository.repository.AuthorRepository;
import com.example.jparepository.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;


@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final EntityManager entityManager;
    private final AuthorService authorService;

    @Transactional(propagation = Propagation.REQUIRED)
    public void putBookAndAuthor(){
        Book book = new Book();
        book.setName("JPA 시작하기");
        bookRepository.save(book);

        authorService.putAuthor();

//        Author author = new Author();
//        author.setName("martin");
//        authorRepository.save(author);
    }

    @Transactional
    public void putBookAndAuthorError(){
        Book book = new Book();
        book.setName("JPA 시작하기");
        bookRepository.save(book);

        Author author = new Author();
        author.setName("martin");
        authorRepository.save(author);

        throw new RuntimeException("Error Occur Test");
    }
    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void get(Long id){
        System.out.println(">>> " + bookRepository.findById(id));
        System.out.println(">>> " + bookRepository.findAll());
        entityManager.clear(); // UNREPEATABLE 문제 발생

        System.out.println(">>> " + bookRepository.findById(id));
        System.out.println(">>> " + bookRepository.findAll());
        entityManager.clear(); // UNREPEATABLE 문제 발생
    }
}
