package wh.duckbill.netflix.repository.token;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wh.duckbill.netflix.entity.token.TokenEntity;

import java.util.Optional;

import static wh.duckbill.netflix.entity.token.QTokenEntity.tokenEntity;

@Repository
@RequiredArgsConstructor
public class TokenCustomRepositoryImpl implements TokenCustomRepository {
  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public Optional<TokenEntity> findByUserId(String userId) {
    return jpaQueryFactory.selectFrom(tokenEntity)
        .where(tokenEntity.userId.eq(userId))
        .fetch()
        .stream().findFirst();
  }
}
