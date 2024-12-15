package wh.duckbill.netflix.controller.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegisterRequest {
  private final String username;
  private final String password;
  private final String email;
  private final String phone;
}
