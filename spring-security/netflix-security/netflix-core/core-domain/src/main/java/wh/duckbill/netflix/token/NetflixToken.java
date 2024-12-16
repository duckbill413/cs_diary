package wh.duckbill.netflix.token;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class NetflixToken {
  private final String accessToken;
  private final String refreshToken;
  private final LocalDateTime accessTokenExpiresAt;
  private final LocalDateTime refreshTokenExpiresAt;
}
