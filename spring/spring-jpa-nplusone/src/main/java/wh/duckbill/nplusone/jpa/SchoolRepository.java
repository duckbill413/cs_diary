package wh.duckbill.nplusone.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.nplusone.batchsize.eager.School;

public interface SchoolRepository extends JpaRepository<School, Long> {
}
