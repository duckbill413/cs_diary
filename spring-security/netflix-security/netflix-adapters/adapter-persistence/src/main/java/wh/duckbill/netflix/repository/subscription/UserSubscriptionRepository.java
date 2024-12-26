package wh.duckbill.netflix.repository.subscription;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import wh.duckbill.netflix.entity.subscription.UserSubscriptionEntity;
import wh.duckbill.netflix.subscription.FetchUserSubscriptionPort;
import wh.duckbill.netflix.subscription.InsertUserSubscriptionPort;
import wh.duckbill.netflix.subscription.UpdateUserSubscriptionPort;
import wh.duckbill.netflix.subscription.UserSubscription;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserSubscriptionRepository implements FetchUserSubscriptionPort, InsertUserSubscriptionPort, UpdateUserSubscriptionPort {
  private final UserSubscriptionJpaRepository userSubscriptionJpaRepository;

  @Transactional(readOnly=true)
  @Override
  public Optional<UserSubscription> findByUserId(String userId) {
    return userSubscriptionJpaRepository.findByUserId(userId)
        .map(UserSubscriptionEntity::toDomain);
  }

  @Transactional
  @Override
  public void create(String userId) {
    UserSubscription userSubscription = UserSubscription.newSubscription(userId);
    userSubscriptionJpaRepository.save(UserSubscriptionEntity.fromDomain(userSubscription));
  }

  @Transactional
  @Override
  public void update(UserSubscription userSubscription) {
    userSubscriptionJpaRepository.save(UserSubscriptionEntity.fromDomain(userSubscription));
  }
}
