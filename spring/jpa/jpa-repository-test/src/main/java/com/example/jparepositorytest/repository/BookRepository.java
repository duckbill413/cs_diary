package com.example.jparepositorytest.repository;

import com.example.jparepositorytest.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
