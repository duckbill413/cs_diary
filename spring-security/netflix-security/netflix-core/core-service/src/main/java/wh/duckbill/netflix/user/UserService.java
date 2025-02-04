package wh.duckbill.netflix.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wh.duckbill.netflix.exception.UserException;
import wh.duckbill.netflix.user.command.UserRegisterationCommand;
import wh.duckbill.netflix.user.command.UserResponse;
import wh.duckbill.netflix.user.response.UserRegisterationResponse;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements FetchUserUsecase, RegisterUserUsecase {
  private final KakaoUserPort kakaoUserPort;
  private final FetchUserPort fetchUserPort;
  private final InsertUserPort insertUserPort;

  @Override
  public UserResponse findUserByEmail(String email) {
    Optional<UserPortResponse> byEmail = fetchUserPort.findByEmail(email);
    if (byEmail.isEmpty()) {
      throw new UserException.UserDoesNotExistException();
    }

    UserPortResponse userPortResponse = byEmail.get();
    return UserResponse.builder()
        .userId(userPortResponse.getUserId())
        .email(userPortResponse.getEmail())
        .phone(userPortResponse.getPhone())
        .password(userPortResponse.getPassword())
        .provider(userPortResponse.getProvider())
        .providerId(userPortResponse.getProviderId())
        .role(userPortResponse.getRole())
        .username(userPortResponse.getUsername())
        .build();
  }

  @Override
  public UserResponse findByProviderId(String providerId) {
    return fetchUserPort.findByProviderId(providerId)
        .map(it -> UserResponse.builder()
            .providerId(it.getProviderId())
            .provider(it.getProvider())
            .username(it.getUsername())
            .role(it.getRole())
            .build())
        .orElse(null);
  }

  @Override
  public UserResponse findKakaoUser(String accessToken) {
    UserPortResponse userFromKakao = kakaoUserPort.findUserFromKakao(accessToken);
    return UserResponse.builder()
        .provider(userFromKakao.getProvider())
        .providerId(userFromKakao.getProviderId())
        .username(userFromKakao.getUsername())
        .build();
  }

  @Override
  public UserRegisterationResponse register(UserRegisterationCommand command) {
    String email = command.getEmail();
    // 사용자 조회
    Optional<UserPortResponse> byEmail = fetchUserPort.findByEmail(email);
    if (byEmail.isPresent()) {
      throw new UserException.UserAlreadyExistsException();
    }

    // 없으면? 회원가입 시도
    UserPortResponse response = insertUserPort.create(
        CreateUser.builder()
            .email(command.getEmail())
            .encryptedPassword(command.getEncryptedPassword())
            .username(command.getUsername())
            .phone(command.getPhone())
            .build()
    );

    return new UserRegisterationResponse(response.getUsername(), response.getEmail(), response.getPhone());
  }

  @Override
  public UserRegisterationResponse registerSocialUser(String username, String providerId, String provider) {
    Optional<UserPortResponse> byProviderId = fetchUserPort.findByProviderId(providerId);
    if (byProviderId.isPresent()) {
      return null;
    }
    UserPortResponse socialUser = insertUserPort.createSocialUser(username, provider, providerId);
    return new UserRegisterationResponse(socialUser.getUsername(), null, null);
  }
}
