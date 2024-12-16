package wh.duckbill.netflix.repository.sample;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wh.duckbill.netflix.entity.QSampleEntity;
import wh.duckbill.netflix.entity.sample.SampleEntity;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SampleCustomRepositoryImpl implements SampleCustomRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public List<SampleEntity> findAllByABC() {
        return queryFactory.selectFrom(QSampleEntity.sampleEntity)
                .fetch();
    }
}
