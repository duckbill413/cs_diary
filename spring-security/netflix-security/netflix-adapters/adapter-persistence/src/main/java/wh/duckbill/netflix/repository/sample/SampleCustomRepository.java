package wh.duckbill.netflix.repository.sample;

import wh.duckbill.netflix.entity.sample.SampleEntity;

import java.util.List;

public interface SampleCustomRepository {
    List<SampleEntity> findAllByABC();
}
