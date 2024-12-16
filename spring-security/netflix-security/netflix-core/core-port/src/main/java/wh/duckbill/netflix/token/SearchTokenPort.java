package wh.duckbill.netflix.token;

import java.util.Optional;

public interface SearchTokenPort {
  Optional<TokenPortResponse> findByUserId(String userId);
}
