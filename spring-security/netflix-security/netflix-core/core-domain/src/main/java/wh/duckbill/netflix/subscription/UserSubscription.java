package wh.duckbill.netflix.subscription;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class UserSubscription {
  private String userId;
  private SubscriptionType subscriptionType;
  private LocalDateTime startAt;
  private LocalDateTime endAt;
  private Boolean validYn;

  public void off() {
    this.endAt = LocalDateTime.now();
    this.validYn = false;
  }

  public void renew() {
    this.startAt = LocalDateTime.now();
    this.endAt = getEndAt(this.startAt);
    this.validYn = true;
  }

  private static LocalDateTime getEndAt(LocalDateTime startAt) {
    return startAt.plusDays(30);
  }

  public void change(SubscriptionType subscriptionType) {
    this.subscriptionType = subscriptionType;
  }

  public boolean ableToRenew() {
    LocalDateTime now = LocalDateTime.now();
    return now.isAfter(endAt);
  }

  public boolean ableToChange() {
    LocalDateTime now = LocalDateTime.now();
    return now.isBefore(endAt) && now.isAfter(startAt) && validYn;
  }

  public static UserSubscription newSubscription(String userId) {
    LocalDateTime now = LocalDateTime.now();
    return UserSubscription.builder()
        .userId(userId)
        .subscriptionType(SubscriptionType.FREE)
        .startAt(now)
        .endAt(getEndAt(now))
        .validYn(true)
        .build();
  }
}
