package com.example.jparepository.repository;

import com.example.jparepository.domain.Book;
import com.example.jparepository.repository.dto.BookNameAndCategory;
import com.example.jparepository.repository.dto.BookNameAndCategoryClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByName(String name);

    @Modifying
    @Query(value = "update book set category='none'", nativeQuery = true)
    void update();

    List<Book> findByCategoryIsNull();

    List<Book> findAllByDeletedFalse();

    List<Book> findByCategoryIsNullAndDeletedFalse();

    // FEAT: JPQL: DB 와는 좀 다르게 Entity 를 참조하여 쿼리를 생성하면 된다.
    @Query(value = "select b from Book b " +
            "where name = ?1 and createdAt >= ?2 and updatedAt >= ?3 and category is null")
    List<Book> findByNameRecently(
            String name,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    );
    @Query(value = "select b from Book b " +
            "where name = :name and createdAt >= :createdAt and updatedAt >= :updatedAt and category is null")
    List<Book> findByNameRecently2(
            @Param(value = "name") String name,
            @Param("createdAt") LocalDateTime createdAt,
            @Param("updatedAt") LocalDateTime updatedAt
    );
    @Query(value = "select b.name as name, b.category as category " +
            "from Book b")
    List<BookNameAndCategory>  findBookNameAndCategory();
    @Query(value = "select new com.example.jparepository.repository.dto.BookNameAndCategoryClass(b.name, b.category) from Book b")
    List<BookNameAndCategoryClass> findBookNameAndCategoryByClass();
    @Query(value = "select new com.example.jparepository.repository.dto.BookNameAndCategoryClass(b.name, b.category) from Book b")
    Page<BookNameAndCategoryClass> findBookNameAndCategoryByPage(PageRequest of);
}
