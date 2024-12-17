package wh.duckbill.netflix.repository.token;

import wh.duckbill.netflix.entity.token.TokenEntity;

import java.util.Optional;

public interface TokenCustomRepository {
  Optional<TokenEntity> findByUserId(String userId);
}
