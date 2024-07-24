package wh.duckbill.nplusone.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.nplusone.subselect.lazy.BookStore;

public interface BookStoreRepository extends JpaRepository<BookStore, Long> {
}
