package wh.duckbill.netflix.controller.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import wh.duckbill.netflix.annotation.PasswordEncryption;

@Getter
@AllArgsConstructor
public class UserRegisterRequest {
  private final String username;
  @PasswordEncryption
  private String password;
  private final String email;
  private final String phone;
}
