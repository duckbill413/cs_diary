package wh.duckbill.netflix.repository.sample;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.netflix.entity.sample.SampleEntity;

public interface SampleJpaRepository extends JpaRepository<SampleEntity, String>, SampleCustomRepository {
}
