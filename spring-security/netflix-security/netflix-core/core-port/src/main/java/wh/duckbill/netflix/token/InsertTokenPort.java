package wh.duckbill.netflix.token;

public interface InsertTokenPort {
  TokenPortResponse create(String userId, String accessToken, String refreshToken);
}
