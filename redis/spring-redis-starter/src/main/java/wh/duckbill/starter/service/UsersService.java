package wh.duckbill.starter.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import wh.duckbill.starter.dto.UserProfile;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class UsersService {
    private final ExternalUsersService externalUsersService;
    private final StringRedisTemplate redisTemplate;

    public UserProfile getUserProfile(String userId) {
        String userName;

        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        String cachedName = ops.get("nameKey:" + userId);
        if (cachedName != null) {
            userName = cachedName;
        } else {
            userName = externalUsersService.getUserName(userId);
            ops.set("nameKey:" + userId, userName, 5, TimeUnit.SECONDS);
        }

        int userAge = externalUsersService.getUserAge(userId);

        return new UserProfile(userName, userAge);
    }
}
