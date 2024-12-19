package wh.duckbill.netflix.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import wh.duckbill.netflix.controller.NetflixApiResponse;
import wh.duckbill.netflix.controller.user.request.UserLoginRequest;
import wh.duckbill.netflix.controller.user.request.UserRegisterRequest;
import wh.duckbill.netflix.security.NetflixAuthUser;
import wh.duckbill.netflix.token.FetchTokenUsecase;
import wh.duckbill.netflix.user.FetchUserUsecase;
import wh.duckbill.netflix.user.RegisterUserUsecase;
import wh.duckbill.netflix.user.command.UserRegisterationCommand;
import wh.duckbill.netflix.user.command.UserResponse;
import wh.duckbill.netflix.user.response.UserRegisterationResponse;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {
  private final RegisterUserUsecase registerUserUsecase;
  private final AuthenticationManagerBuilder authenticationManagerBuilder;
  private final FetchTokenUsecase fetchTokenUsecase;
  private final FetchUserUsecase fetchUserUsecase;

  @PostMapping("/api/v1/user/register")
  public NetflixApiResponse<UserRegisterationResponse> register(@RequestBody UserRegisterRequest request) {
    UserRegisterationResponse register = registerUserUsecase.register(
        UserRegisterationCommand.builder()
            .username(request.getUsername())
            .encryptedPassword(request.getPassword())
            .email(request.getEmail())
            .phone(request.getPhone())
            .build()
    );

    return NetflixApiResponse.ok(register);
  }

  @PostMapping("/api/v1/user/login")
  public NetflixApiResponse<String> login(@RequestBody UserLoginRequest request) {
    String email = request.getEmail();
    String password = request.getPassword();

    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(email, password);
    Authentication authenticate = authenticationManagerBuilder.getObject().authenticate(token);

    NetflixAuthUser principal = (NetflixAuthUser) authenticate.getPrincipal();
    return NetflixApiResponse.ok("access-token");
  }

  @PostMapping("/api/v1/user/callback")
  public NetflixApiResponse<String> kakaoCallback(@RequestBody Map<String, String> request) {
    String code = request.get("code");

    String accessTokenFromKakao = fetchTokenUsecase.getTokenFromKakao(code);
    UserResponse kakaoUser = fetchUserUsecase.findKakaoUser(accessTokenFromKakao);
    return NetflixApiResponse.ok(null);
  }
}
