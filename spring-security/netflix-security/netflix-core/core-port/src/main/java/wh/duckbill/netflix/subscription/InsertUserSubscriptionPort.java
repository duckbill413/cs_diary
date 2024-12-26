package wh.duckbill.netflix.subscription;

public interface InsertUserSubscriptionPort {
  void create(String userId);
}
