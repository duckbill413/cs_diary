package wh.duckbill.netflix.user;

import wh.duckbill.netflix.user.command.UserRegisterationCommand;
import wh.duckbill.netflix.user.response.UserRegisterationResponse;

public interface RegisterUserUsecase {
  UserRegisterationResponse register(UserRegisterationCommand command);
  UserRegisterationResponse registerSocialUser(String username, String providerId, String provider);
}
