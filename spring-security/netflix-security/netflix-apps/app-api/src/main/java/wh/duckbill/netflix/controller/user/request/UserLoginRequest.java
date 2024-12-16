package wh.duckbill.netflix.controller.user.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserLoginRequest {
  private final String email;
  private final String password;
}
