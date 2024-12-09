package wh.duckbill.netflix.repository;

import wh.duckbill.netflix.entity.SampleEntity;

import java.util.List;

public interface SampleCustomRepository {
    List<SampleEntity> findAll();
}
