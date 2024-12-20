package wh.duckbill.netflix.repository.user.social;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.netflix.entity.user.SocialUserEntity;

import java.util.Optional;

public interface SocialUserJpaRepository extends JpaRepository<SocialUserEntity, String> {
  Optional<SocialUserEntity> findByProviderId(String providerId);
}
