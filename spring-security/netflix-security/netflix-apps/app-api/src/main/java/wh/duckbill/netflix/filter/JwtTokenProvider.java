package wh.duckbill.netflix.filter;

import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import wh.duckbill.netflix.token.FetchTokenUsecase;
import wh.duckbill.netflix.user.command.UserResponse;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
  private final FetchTokenUsecase fetchTokenUsecase;

  public Authentication getAuthentication(String accessToken) {
    UserResponse user = fetchTokenUsecase.findUserByAccessToken(accessToken);
    List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(user.getRole()));
    UserDetails principal = new User(
        user.getUsername(),
        StringUtils.isBlank(user.getPassword()) ? "password" : user.getPassword(),
        authorities
    );
    return new UsernamePasswordAuthenticationToken(principal, user.getUserId(), authorities);
  }
}
