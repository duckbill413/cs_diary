package wh.duckbill.netflix.token;

public interface KakaoTokenPort {
  String getAccessTokenByCode(String code);
}
