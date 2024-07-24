package wh.duckbill.nplusone;

import jakarta.persistence.EntityManager;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.nplusone.jpa.BookRepository;
import wh.duckbill.nplusone.jpa.BookStoreRepository;
import wh.duckbill.nplusone.subselect.lazy.Book;
import wh.duckbill.nplusone.subselect.lazy.BookStore;

import static org.jeasy.random.FieldPredicates.*;

@SpringBootTest
public class Eager_SubSelectTests {
    @Autowired
    private BookStoreRepository bookStoreRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private EntityManager entityManager;

    EasyRandomParameters parameters = new EasyRandomParameters()
            .excludeField(named("id").and(ofType(Long.class)).and(inClass(Book.class)))
            .excludeField(named("id").and(ofType(Long.class)).and(inClass(BookStore.class)));

    EasyRandom easyRandom = new EasyRandom(parameters);

    /**
     * FetchType.EAGER - FetchMode.SUBSELECT
     * IN 절을 사용하여 데이터를 조회
     * 하위 데이터를 조회하지 않는 경우도 IN 절로 데이터 요청 발생
     */
    @Transactional
    @DisplayName("EAGER - FetchMode.SUBSELECT 하위 데이터 조회 X")
    @Test
    public void test1() {
        for (int i = 0; i < 10; i++) {
            BookStore bookStore = easyRandom.nextObject(BookStore.class);
            bookStoreRepository.save(bookStore);
            Book book = easyRandom.nextObject(Book.class);
            book.setBookStore(bookStore);
            bookStore.getBooks().add(book);
            bookRepository.save(book);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        var bookStores = bookStoreRepository.findAll();
    }

    /**
     * FetchType.EAGER - FetchMode.SUBSELECT
     * IN 절을 사용하여 데이터를 조회
     * 하위 데이터를 조회하는 경우
     * 추가 쿼리 발생 X
     */
    @Transactional
    @DisplayName("EAGER - FetchMode.SUBSELECT 하위 데이터 조회 O")
    @Test
    public void test2() {
        for (int i = 0; i < 10; i++) {
            BookStore bookStore = easyRandom.nextObject(BookStore.class);
            bookStoreRepository.save(bookStore);
            Book book = easyRandom.nextObject(Book.class);
            book.setBookStore(bookStore);
            bookStore.getBooks().add(book);
            bookRepository.save(book);
        }

        System.out.println("========================================");
        System.out.println("========================================");
        System.out.println("========================================");
        entityManager.clear();
        var bookStores = bookStoreRepository.findAll();
        var names = bookStores.stream().flatMap(bookStore -> bookStore.getBooks().stream().map(Book::getName)).toList();
        System.out.println(names);
    }
}
