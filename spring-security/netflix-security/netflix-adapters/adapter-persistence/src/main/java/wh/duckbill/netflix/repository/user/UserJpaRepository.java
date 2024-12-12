package wh.duckbill.netflix.repository.user;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.netflix.entity.user.UserEntity;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
}
