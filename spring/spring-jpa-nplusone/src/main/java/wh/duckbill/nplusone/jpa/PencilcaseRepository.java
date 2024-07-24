package wh.duckbill.nplusone.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.nplusone.batchsize.lazy.Pencilcase;

public interface PencilcaseRepository extends JpaRepository<Pencilcase, Long> {
}
