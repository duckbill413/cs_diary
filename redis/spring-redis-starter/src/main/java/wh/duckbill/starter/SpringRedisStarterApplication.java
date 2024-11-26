package wh.duckbill.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SpringRedisStarterApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRedisStarterApplication.class, args);
    }

}
