package wh.duckbill.netflix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.netflix.entity.SampleEntity;

public interface SampleJpaRepository extends JpaRepository<SampleEntity, String>, SampleCustomRepository {
}
