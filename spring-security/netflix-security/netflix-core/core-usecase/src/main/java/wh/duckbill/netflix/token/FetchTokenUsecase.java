package wh.duckbill.netflix.token;

import wh.duckbill.netflix.user.command.UserResponse;

public interface FetchTokenUsecase {
  Boolean validateToken(String accessToken);

  String getTokenFromKakao(String code);

  UserResponse findUserByAccessToken(String accessToken);
}
