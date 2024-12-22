package wh.duckbill.netflix.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import wh.duckbill.netflix.token.*;
import wh.duckbill.netflix.token.response.TokenResponse;
import wh.duckbill.netflix.user.FetchUserUsecase;
import wh.duckbill.netflix.user.command.UserResponse;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenService implements CreateTokenUsecase, FetchTokenUsecase, UpdateTokenUsecase {
  @Value("${jwt.secret}")
  private String secretKey;
  @Value("${jwt.expire.access-token}")
  private Integer accessTokenExpireHour;
  @Value("${jwt.expire.refresh-token}")
  private Integer refreshTokenExpireHour;

  private final InsertTokenPort insertTokenPort;
  private final UpdateTokenPort updateTokenPort;
  private final SearchTokenPort searchTokenPort;
  private final FetchUserUsecase fetchUserUsecase;
  private final KakaoTokenPort kakaoTokenPort;

  @Override
  public TokenResponse createNewToken(String userId) {
    String accessToken = getToken(userId, Duration.ofHours(accessTokenExpireHour));
    String refreshToken = getToken(userId, Duration.ofHours(refreshTokenExpireHour));

    TokenPortResponse tokenPortResponse = insertTokenPort.create(userId, accessToken, refreshToken);
    return TokenResponse.builder()
        .accessToken(tokenPortResponse.getAccessToken())
        .refreshToken(tokenPortResponse.getRefreshToken())
        .build();
  }

  @Override
  public Boolean validateToken(String accessToken) {
    Jwts.parser()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(accessToken);
    return true;
  }

  @Override
  public String getTokenFromKakao(String code) {
    return kakaoTokenPort.getAccessTokenByCode(code);
  }

  @Override
  public UserResponse findUserByAccessToken(String accessToken) {
    Claims claims = parseClaims(accessToken);

    Object userId = claims.get("userId");
    if (ObjectUtils.isEmpty(userId)) {
      throw new RuntimeException("권한 정보가 없는 토큰 입니다.");
    }

    return fetchUserUsecase.findByProviderId((String) userId);
  }

  private Claims parseClaims(String accessToken) {
    try {
      return Jwts.parser()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(accessToken).getBody();
    } catch (ExpiredJwtException e) {
      return e.getClaims();
    }
  }

  private String getToken(String userId, Duration expireAt) {
    Date now = new Date();
    Instant instant = now.toInstant();

    return Jwts.builder()
        .claim("userId", userId)
        .issuedAt(now)
        .expiration(Date.from(instant.plus(expireAt)))
        .signWith(getSigningKey())
        .compact();
  }

  private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  @Override
  public TokenResponse upsertToken(String userId) {
    return searchTokenPort.findByUserId(userId)
        .map(token -> updateNewToken(userId))
        .orElseGet(() -> createNewToken(userId));
  }

  @Override
  public TokenResponse updateNewToken(String userId) {
    String accessToken = getToken(userId, Duration.ofHours(accessTokenExpireHour));
    String refreshToken = getToken(userId, Duration.ofHours(refreshTokenExpireHour));
    updateTokenPort.updateToken(userId, accessToken, refreshToken);
    return TokenResponse.builder()
        .accessToken(accessToken)
        .refreshToken(refreshToken)
        .build();
  }

  @Override
  public TokenResponse reissueToken(String accessToken, String refreshToken) {
    Jws<Claims> claimsJws = Jwts.parser()
        .setSigningKey(secretKey)
        .build()
        .parseClaimsJws(accessToken);

    String userId = (String) claimsJws.getPayload().get("userId");
    Optional<TokenPortResponse> byUserId = searchTokenPort.findByUserId(userId);
    if (byUserId.isEmpty()) {
      throw new RuntimeException("토큰이 유효하지 않습니다.");
    }

    TokenPortResponse tokenPortResponse = byUserId.get();
    if (!tokenPortResponse.getRefreshToken().equals(refreshToken)) {
      throw new RuntimeException("리프레시 토큰이 유효하지 않습니다.");
    }

    return updateNewToken(userId);
  }
}
