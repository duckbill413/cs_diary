package wh.duckbill.netflix.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import wh.duckbill.netflix.security.NetflixUserDetailsService;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
  private final NetflixUserDetailsService netflixUserDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.httpBasic(AbstractHttpConfigurer::disable);
    http.formLogin(AbstractHttpConfigurer::disable);
    http.csrf(AbstractHttpConfigurer::disable);
    http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

    http.userDetailsService(netflixUserDetailsService);

    http.authorizeHttpRequests(auth ->
        auth.requestMatchers(
                "/api/v1/user/register",
                "/api/v1/auth/login").permitAll()
            .anyRequest().authenticated());

//    http.oauth2Login(oauth2 ->
//        oauth2.failureUrl("/login?error=true"));

    return http.build();
  }

  private CorsConfigurationSource corsConfigurationSource() {
    return request -> {
      CorsConfiguration configuration = new CorsConfiguration();
      configuration.setAllowedHeaders(Collections.singletonList("*"));
      configuration.setAllowedMethods(Collections.singletonList("*"));
      configuration.setAllowedOriginPatterns(Collections.singletonList("*"));
      configuration.setAllowCredentials(true);
      return configuration;
    };
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
