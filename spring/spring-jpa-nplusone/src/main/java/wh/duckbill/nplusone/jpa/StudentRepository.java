package wh.duckbill.nplusone.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.nplusone.batchsize.eager.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
