package wh.duckbill.netflix.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.netflix.entity.user.SocialUserEntity;
import wh.duckbill.netflix.entity.user.UserEntity;
import wh.duckbill.netflix.repository.user.social.SocialUserJpaRepository;
import wh.duckbill.netflix.user.CreateUser;
import wh.duckbill.netflix.user.FetchUserPort;
import wh.duckbill.netflix.user.InsertUserPort;
import wh.duckbill.netflix.user.UserPortResponse;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements FetchUserPort, InsertUserPort {
  private final UserJpaRepository userJpaRepository;
  private final SocialUserJpaRepository socialUserJpaRepository;

  @Transactional(readOnly = true)
  @Override
  public Optional<UserPortResponse> findByEmail(String email) {
    Optional<UserEntity> byEmail = userJpaRepository.findByEmail(email);
    return byEmail.map(userEntity -> UserPortResponse.builder()
        .userId(userEntity.getUserId())
        .password(userEntity.getPassword())
        .username(userEntity.getUsername())
        .email(userEntity.getEmail())
        .phone(userEntity.getPhone())
        .build());
  }

  @Override
  public Optional<UserPortResponse> findByProviderId(String providerId) {
    Optional<SocialUserEntity> byProviderId = socialUserJpaRepository.findByProviderId(providerId);
    if (byProviderId.isEmpty()) {
      return Optional.empty();
    }

    SocialUserEntity socialUserEntity = byProviderId.get();

    return Optional.of(UserPortResponse.builder()
        .provider(socialUserEntity.getProvider())
        .providerId(socialUserEntity.getProviderId())
        .username(socialUserEntity.getUsername())
        .build());
  }

  @Transactional
  @Override
  public UserPortResponse create(CreateUser createUser) {
    UserEntity userEntity = new UserEntity(
        createUser.getUsername(),
        createUser.getEncryptedPassword(),
        createUser.getEmail(),
        createUser.getPhone()
    );

    UserEntity save = userJpaRepository.save(userEntity);

    return UserPortResponse.builder()
        .userId(save.getUserId())
        .username(save.getUsername())
        .password(save.getPassword())
        .email(save.getEmail())
        .phone(save.getPhone())
        .build();
  }

  @Transactional
  @Override
  public UserPortResponse createSocialUser(String username, String provider, String providerId) {
    SocialUserEntity socialUserEntity = new SocialUserEntity(username, provider, providerId);
    socialUserJpaRepository.save(socialUserEntity);
    return UserPortResponse.builder()
        .provider(socialUserEntity.getProvider())
        .providerId(socialUserEntity.getProviderId())
        .username(socialUserEntity.getUsername())
        .build();
  }
}
