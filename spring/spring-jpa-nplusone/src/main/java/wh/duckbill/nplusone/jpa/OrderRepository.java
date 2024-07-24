package wh.duckbill.nplusone.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.nplusone.lazy.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
