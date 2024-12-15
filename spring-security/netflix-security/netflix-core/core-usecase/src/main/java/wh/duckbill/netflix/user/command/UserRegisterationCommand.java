package wh.duckbill.netflix.user.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserRegisterationCommand {
  private final String username;
  private final String encryptedPassword;
  private final String email;
  private final String phone;
}
