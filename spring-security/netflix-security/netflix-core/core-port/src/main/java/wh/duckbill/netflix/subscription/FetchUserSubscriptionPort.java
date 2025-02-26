package wh.duckbill.netflix.subscription;

import java.util.Optional;

public interface FetchUserSubscriptionPort {
  Optional<UserSubscription> findByUserId(String userId);
}
