package wh.duckbill.nplusone.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.nplusone.batchsize.lazy.Pencil;

public interface PencilRepository extends JpaRepository<Pencil, Long> {
}
