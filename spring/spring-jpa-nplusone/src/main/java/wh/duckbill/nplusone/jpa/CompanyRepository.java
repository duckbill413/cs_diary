package wh.duckbill.nplusone.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.nplusone.subselect.eager.Company;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
