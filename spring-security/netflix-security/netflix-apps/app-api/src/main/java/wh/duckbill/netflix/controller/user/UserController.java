package wh.duckbill.netflix.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wh.duckbill.netflix.controller.NetflixApiResponse;
import wh.duckbill.netflix.controller.user.request.UserRegisterRequest;
import wh.duckbill.netflix.user.RegisterUserUsecase;
import wh.duckbill.netflix.user.command.UserRegisterationCommand;
import wh.duckbill.netflix.user.response.UserRegisterationResponse;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final RegisterUserUsecase registerUserUsecase;
  private final PasswordEncoder passwordEncoder;

  @PostMapping("/api/v1/user/register")
  public NetflixApiResponse<UserRegisterationResponse> register(@RequestBody UserRegisterRequest request) {
    UserRegisterationResponse register = registerUserUsecase.register(
        UserRegisterationCommand.builder()
            .username(request.getUsername())
            .encryptedPassword(passwordEncoder.encode(request.getPassword()))
            .email(request.getEmail())
            .phone(request.getPhone())
            .build()
    );

    return NetflixApiResponse.ok(register);
  }

}
