package wh.duckbill.netflix.repository.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import wh.duckbill.netflix.entity.user.UserEntity;
import wh.duckbill.netflix.user.FetchUserPort;
import wh.duckbill.netflix.user.UserPortResponse;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository implements FetchUserPort {
    private final UserJpaRepository userJpaRepository;

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
}
