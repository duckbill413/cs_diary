package wh.duckbill.netflix.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wh.duckbill.netflix.entity.QSampleEntity;
import wh.duckbill.netflix.entity.SampleEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SampleCustomRepositoryImpl implements SampleCustomRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<SampleEntity> findAll() {
        return queryFactory.selectFrom(QSampleEntity.sampleEntity)
                .fetch();
    }
}
