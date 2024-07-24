package wh.duckbill.nplusone.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.nplusone.subselect.eager.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
