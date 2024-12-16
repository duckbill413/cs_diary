package wh.duckbill.netflix.repository.token;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.netflix.token.InsertTokenPort;
import wh.duckbill.netflix.token.SearchTokenPort;
import wh.duckbill.netflix.token.TokenPortResponse;
import wh.duckbill.netflix.token.UpdateTokenPort;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TokenRepository implements SearchTokenPort, InsertTokenPort, UpdateTokenPort {
  private final TokenJpaRepository tokenJpaRepository;

  @Transactional
  @Override
  public TokenPortResponse create(String userId, String accessToken, String refreshToken) {
    return null;
  }

  @Transactional(readOnly = true)
  @Override
  public Optional<TokenPortResponse> findByUserId(String userId) {
    return Optional.empty();
  }

  @Transactional
  @Override
  public void updateToken(String userId, String accessToken, String refreshToken) {

  }
}
