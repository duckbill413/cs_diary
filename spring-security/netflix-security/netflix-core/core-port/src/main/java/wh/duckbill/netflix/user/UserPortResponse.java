package wh.duckbill.netflix.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class UserPortResponse {
    private final String userId;
    private final String username;
    private final String password;
    private final String email;
    private final String phone;
    private final String provider;
    private final String providerId;
    private final String role;
}
