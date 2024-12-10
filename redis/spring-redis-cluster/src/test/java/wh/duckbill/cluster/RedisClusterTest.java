package wh.duckbill.cluster;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class RedisClusterTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    String dummyValue = "banana:";

    @Test
    void setValues() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        for (int i = 0; i < 1000; i++) {
            String key = String.format("name:%d", i);   // name:1
            valueOperations.set(key, dummyValue + i);
        }
    }

    @Test
    void getValues() {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        for (int i = 0; i < 1000; i++) {
            String key = String.format("name:%d", i);
            String value = valueOperations.get(key);

            assertEquals(value, dummyValue + i);
        }
    }
}
