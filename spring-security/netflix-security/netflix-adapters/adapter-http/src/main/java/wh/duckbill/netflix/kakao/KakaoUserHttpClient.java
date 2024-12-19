package wh.duckbill.netflix.kakao;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import wh.duckbill.netflix.user.KakaoUserPort;
import wh.duckbill.netflix.user.UserPortResponse;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class KakaoUserHttpClient implements KakaoUserPort {
  private final String KAKAO_USERINFO_API_URL = "https://kapi.kakao.com/v2/user/me";


  @Override
  public UserPortResponse findUserFromKakao(String accessToken) {
    RestTemplate restTemplate = new RestTemplate();
    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + accessToken);

    HttpEntity<String> entity = new HttpEntity<>(headers);
    ResponseEntity<Map> response = restTemplate.exchange(
        KAKAO_USERINFO_API_URL,
        HttpMethod.GET,
        entity,
        Map.class
    );

    Map properteis = (Map) response.getBody().get("properties");
    String nickname = (String) properteis.get("nickname");
    Long id = (Long) response.getBody().get("id");

    return UserPortResponse.builder()
        .username(nickname)
        .provider(String.valueOf(id))
        .providerId("kakao")
        .build();
  }
}
