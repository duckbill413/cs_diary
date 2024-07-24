package wh.duckbill.nplusone.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.nplusone.eager.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
