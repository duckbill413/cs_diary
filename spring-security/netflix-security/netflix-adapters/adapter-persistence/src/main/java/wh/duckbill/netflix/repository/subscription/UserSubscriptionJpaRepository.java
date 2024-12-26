package wh.duckbill.netflix.repository.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import wh.duckbill.netflix.entity.subscription.UserSubscriptionEntity;

import java.util.Optional;

public interface UserSubscriptionJpaRepository extends JpaRepository<UserSubscriptionEntity, String> {
  Optional<UserSubscriptionEntity> findByUserId(String userId);
}
