package wh.duckbill.netflix.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wh.duckbill.netflix.token.CreateTokenUsecase;
import wh.duckbill.netflix.token.FetchTokenUsecase;
import wh.duckbill.netflix.token.UpdateTokenUsecase;
import wh.duckbill.netflix.token.response.TokenResponse;
import wh.duckbill.netflix.user.command.UserResponse;

@Service
@RequiredArgsConstructor
public class TokenService implements CreateTokenUsecase, FetchTokenUsecase, UpdateTokenUsecase {
  @Override
  public TokenResponse createNewToken(String userId) {
    return null;
  }

  @Override
  public Boolean validateToken(String accessToken) {
    return null;
  }

  @Override
  public String getTokenFromKakao(String code) {
    return "";
  }

  @Override
  public UserResponse findUserByAccessToken(String accessToken) {
    return null;
  }
}
