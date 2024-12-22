package wh.duckbill.netflix.repository.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.netflix.entity.token.TokenEntity;
import wh.duckbill.netflix.token.InsertTokenPort;
import wh.duckbill.netflix.token.SearchTokenPort;
import wh.duckbill.netflix.token.TokenPortResponse;
import wh.duckbill.netflix.token.UpdateTokenPort;

import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenRepository implements SearchTokenPort, InsertTokenPort, UpdateTokenPort {
  private final TokenJpaRepository tokenJpaRepository;

  @Transactional
  @Override
  public TokenPortResponse create(String userId, String accessToken, String refreshToken) {
    TokenEntity entity = TokenEntity.newTokenEntity(userId, accessToken, refreshToken);
    tokenJpaRepository.save(entity);
    return new TokenPortResponse(accessToken, refreshToken);
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<TokenPortResponse> findByUserId(String userId) {
    return tokenJpaRepository.findByUserId(userId)
        .map(token -> new TokenPortResponse(token.getAccessToken(), token.getRefreshToken()));
  }

  @Transactional
  @Override
  public void updateToken(String userId, String accessToken, String refreshToken) {
    Optional<TokenEntity> byUserId = tokenJpaRepository.findByUserId(userId);
    if (byUserId.isEmpty()) {
      throw new RuntimeException();
    }

    TokenEntity entity = byUserId.get();
    entity.updateToken(accessToken, refreshToken);
    tokenJpaRepository.save(entity);
  }
}
