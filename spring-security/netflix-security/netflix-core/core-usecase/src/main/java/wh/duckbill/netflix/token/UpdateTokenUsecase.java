package wh.duckbill.netflix.token;

import wh.duckbill.netflix.token.response.TokenResponse;

public interface UpdateTokenUsecase {
  TokenResponse upsertToken(String userId);
  TokenResponse updateNewToken(String userId);
  TokenResponse reissueToken(String accessToken, String refreshToken);
}
