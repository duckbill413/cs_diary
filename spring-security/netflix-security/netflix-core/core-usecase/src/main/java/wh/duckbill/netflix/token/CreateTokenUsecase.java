package wh.duckbill.netflix.token;

import wh.duckbill.netflix.token.response.TokenResponse;

public interface CreateTokenUsecase {
  TokenResponse createNewToken(String userId);
}
