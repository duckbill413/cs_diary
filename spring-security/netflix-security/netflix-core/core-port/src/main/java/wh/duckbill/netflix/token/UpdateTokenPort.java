package wh.duckbill.netflix.token;

public interface UpdateTokenPort {
  void updateToken(String userId, String accessToken, String refreshToken);
}
