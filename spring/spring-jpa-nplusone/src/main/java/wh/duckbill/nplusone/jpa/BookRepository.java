package wh.duckbill.nplusone.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.nplusone.subselect.lazy.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
