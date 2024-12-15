package wh.duckbill.netflix.user.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserRegisterationResponse {
  private final String username;
  private final String email;
  private final String phone;
}
