package wh.duckbill.netflix.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TokenPortResponse {
  private final String accessToken;
  private final String refreshToken;
}
