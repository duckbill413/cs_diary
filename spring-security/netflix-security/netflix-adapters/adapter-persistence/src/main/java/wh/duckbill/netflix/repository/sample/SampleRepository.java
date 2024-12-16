package wh.duckbill.netflix.repository.sample;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.netflix.entity.sample.SampleEntity;
import wh.duckbill.netflix.sample.SamplePersistencePort;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class SampleRepository implements SamplePersistencePort {
    private final SampleJpaRepository sampleJpaRepository;

    @Transactional
    @Override
    public String getSampleName(String id) {
        sampleJpaRepository.findAllByABC();
        Optional<SampleEntity> byId = sampleJpaRepository.findById(id);
        return byId.map(SampleEntity::getSampleName).orElseThrow();
    }

}
