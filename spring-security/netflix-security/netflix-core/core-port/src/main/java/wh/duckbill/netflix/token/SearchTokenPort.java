package wh.duckbill.netflix.token;

public interface SearchTokenPort {
  TokenPortResponse findByUserId(String userId);
}
