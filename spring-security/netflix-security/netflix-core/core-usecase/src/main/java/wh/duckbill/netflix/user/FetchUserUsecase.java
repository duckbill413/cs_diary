package wh.duckbill.netflix.user;

import wh.duckbill.netflix.user.command.UserResponse;

public interface FetchUserUsecase {
  UserResponse findUserByEmail(String email);

  UserResponse findByProviderId(String providerId);

  UserResponse findKakaoUser(String accessToken);
}
