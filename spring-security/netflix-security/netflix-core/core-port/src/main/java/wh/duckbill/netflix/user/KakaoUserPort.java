package wh.duckbill.netflix.user;

public interface KakaoUserPort {
  UserPortResponse findUserFromKakao(String accessToken);
}
